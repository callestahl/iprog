import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * class that implements a server that listens for client connections,
 * handles messages from clients, and updates a GUI with the current status
 */
public class Server implements MessageObserver {

  private int port = 2000;
  ServerSocket socket = null;
  String hostName = "unknown";
  GUI gui = null;
  private boolean listening = true;

  // thread safe list of objects that control the connections to clients
  List<ClientHandler> clients = new CopyOnWriteArrayList<ClientHandler>();

  /**
   * listens for incoming client connections and starts a new thread for each client.
   */
  private void listen() {
    while (listening) {
      try {
        Socket clientSocket = socket.accept();
        ClientHandler client = new ClientHandler(clientSocket);
        client.addObserver(this);
        new Thread(client).start();
        synchronized (clients) {
          clients.add(client);
        }
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * The main method to start the server.
   *
   * @param args Command line arguments to specify the port number.
   */
  public static void main(String[] args) {
    Server server = new Server();

    // handle command line arguments
    if (args.length == 1) {
      try {
        server.port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.err.println("Could not parse port number");
        System.exit(1);
      }
    } else if (args.length > 1) {
      System.err.println("Wrong number of arguments");
      System.exit(1);
    }

    // initialize the server socket
    try {
      server.socket = new ServerSocket(server.port);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    // create the GUI
    server.gui = new GUI();

    // handle window closing and close connections
    server.gui.addWindowListener(
      new WindowAdapter() {
        @Override
        public synchronized void windowClosing(WindowEvent e) {
          server.listening = false;
          //List<ClientHandler> clientsCopy = new ArrayList<>(server.clients);
          for (ClientHandler clientHandler : server.clients) {
            clientHandler.writeMessage("Server shutting down");
            clientHandler.closeConnections();
          }
          try {
            server.socket.close();
          } catch (IOException e1) {
            System.err.println(e1.getMessage());
          }
        }
      }
    );

    try {
      InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    // set the hostname of the server
    try {
      server.hostName = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      System.err.println(e.getMessage());
    }

    // listen to connections
    new Thread(() -> server.listen()).start();

    // update title of GUI
    new Thread(() -> {
      while (server.listening) {
        synchronized (server.clients) {
          server.gui.setTitle(
            "Host: " +
            server.hostName +
            ", port: " +
            Integer.toString(server.port) +
            ", connected clients: " +
            server.clients.size()
          );
        }
      }
    })
      .start();
  }

  @Override
  public synchronized void messageSent(ClientHandlerMessage message) {
    // remove the client if it has disconnected
    if (message.getMessageType() == MessageType.DISCONNECTED) {
      clients.remove(message.getSender());
    }
    // update GUI and clients about message
    gui.addMessage(message.getSender().getName() + ": " + message.getMessage());
    for (ClientHandler clientHandler : clients) {
      clientHandler.writeMessage(
        message.getSender().getName() + ": " + message.getMessage()
      );
    }
  }
}
