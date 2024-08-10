import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

  private String host = "127.0.0.1";
  private int port = 2000;

  public static void main(String[] args) {
    Client client = new Client();
    switch (args.length) {
      case 0 -> {
      }
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

    Socket socket = null;
    try {
      socket = new Socket(client.host, client.port);
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

    BufferedReader in = null;
    try {
      in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    PrintWriter out = null;
    try {
      out = new PrintWriter(
          new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"),
          true);
    } catch (UnsupportedEncodingException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    while (true) {
      String message = null;
      try {
        message = in.readLine();
      } catch (IOException e) { }
      if (message != null) {
        System.out.println(message);
      }
    }
  }
}
