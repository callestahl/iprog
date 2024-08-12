import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private boolean running = true;
  private Socket socket = null;
  private BufferedReader in = null;
  private PrintWriter out = null;

  public ClientHandler(Socket socket) {
    this.socket = socket;
    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    try {
      out =
        new PrintWriter(
          new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"),
          true
        );
    } catch (UnsupportedEncodingException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  @Override
  public void run() {
    while (running) {
      String message;
      try {
        message = in.readLine();
        if (message == null) {
          running = false;
        }
        else if (!message.isBlank()) {
          System.out.println(message);
        }
      } catch (IOException e) {
        running = false;
      }
    }
  }
}
