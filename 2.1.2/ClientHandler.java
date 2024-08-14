import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable, MessageObservable {

  private boolean running = true;
  private Socket socket = null;
  private BufferedReader in = null;
  private PrintWriter out = null;
  private String name = null;

  private List<MessageObserver> observers = new CopyOnWriteArrayList<MessageObserver>();

  public ClientHandler(Socket socket) {
    this.socket = socket;
    try {
      in =
        new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
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

    name = socket.getInetAddress().getHostAddress();
  }

  public void closeConnections() {
    running = false;
    try {
      out.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    try {
      in.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    try {
      socket.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  @Override
  public void run() {
    notifyObservers(
      new ClientHandlerMessage(
        this,
        " connected to the server",
        MessageType.MESSAGE
      )
    );

    while (running) {
      String message;
      try {
        message = in.readLine();
        if (message == null) {
          running = false;
        } else if (!message.isBlank()) {
          notifyObservers(
            new ClientHandlerMessage(this, message, MessageType.MESSAGE)
          );
        }
      } catch (IOException e) {
        running = false;
      }
    }
    notifyObservers(
      new ClientHandlerMessage(
        this,
        " disconnected from the server",
        MessageType.DISCONNECTED
      )
    );
  }

  @Override
  public synchronized void notifyObservers(ClientHandlerMessage message) {
    for (MessageObserver observer : observers) {
      observer.messageSent(message);
    }
  }

  @Override
  public synchronized void addObserver(MessageObserver observer) {
    observers.add(observer);
  }

  public void writeMessage(String message) {
    out.println(message);
  }

  public String getName() {
    return this.name;
  }
}
