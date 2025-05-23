import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client class that connects to a server, sends messages, and receives responses.
 * Implements the MessageObserver interface to handle sent messages.
 */
public class Client implements MessageObserver {

  private String host = "127.0.0.1";
  private int port = 2000;
  private PrintWriter out = null;
  private Socket socket = null;
  private BufferedReader in = null;
  GUI gui = null;

  /**
   * Main method to start the client.
   *
   * @param args command line arguments for host and port.
   */
  public static void main(String[] args) {
    Client client = new Client();
    
    // handle command line arguments
    switch (args.length) {
      case 0 -> {}
      case 1 -> client.host = args[0];
      case 2 -> {
        client.host = args[0];
        try {
          client.port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
          System.err.println("Invalid port number");
          System.exit(1);
        }
      }
      default -> {
        System.err.println("Wrong number of arguments");
        System.exit(1);
      }
    }

    // connect to the server
    try {
      client.socket = new Socket(client.host, client.port);
    } catch (UnknownHostException e) {
      System.err.println("Invalid host: " + e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println("I/O error: " + e.getMessage());
      System.exit(1);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    // input stream
    try {
      client.in =
        new BufferedReader(
          new InputStreamReader(client.socket.getInputStream())
        );
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    // output stream
    try {
      client.out =
        new PrintWriter(
          new OutputStreamWriter(client.socket.getOutputStream(), "ISO-8859-1"),
          true
        );
    } catch (UnsupportedEncodingException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    // set up the GUI
    client.gui = new GUI();
    client.gui.setTitle(
      "Connected to host " +
      client.host +
      ", port " +
      Integer.toString(client.port)
    );
    client.gui.addObserver(client);

    // listen to incoming messages
    new Thread(() -> {
      while (true) {
        String message = null;
        try {
          message = client.in.readLine();
        } catch (IOException e) {}
        if (message != null && !message.isEmpty()) {
          client.gui.addMessage(message);
        }
      }
    })
      .start();

    // handle window closing
    client.gui.addWindowListener(
      new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          try {
            client.out.close();
          } catch (Exception e1) {
            System.err.println(e1.getMessage());
          }
          try {
            client.in.close();
          } catch (IOException e1) {
            System.err.println(e1.getMessage());
          }
          try {
            client.socket.close();
          } catch (IOException e1) {
            System.err.println(e1.getMessage());
          }
        }
      }
    );
  }

  @Override
  public void messageSent(String message) {
    out.println(message);
  }
}
