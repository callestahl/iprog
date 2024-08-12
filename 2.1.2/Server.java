import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;


public class Server {

  private int port = 2000;
  ServerSocket socket = null;
  String hostName = "unknown";
  GUI gui = null;
  private volatile boolean listening = true;

  volatile List<ClientHandler> clients = new ArrayList<ClientHandler>();

  private void listen() {
    while (listening) {
      try {
        Socket clientSocket = socket.accept();
        ClientHandler client = new ClientHandler(clientSocket);
        new Thread(client).start();
        clients.add(client);
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    Server server = new Server();

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

    try {
      server.socket = new ServerSocket(server.port);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    server.gui = new GUI();
    try {
      InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    new Thread(() -> server.listen()).start();
    while (true) {
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
}
