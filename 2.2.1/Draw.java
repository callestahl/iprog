import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.*;

// baserat på exemplet från uppgiftsbeskkrivningen
public class Draw extends JFrame implements AddPointListener {

  private Paper paper;
  private int myPort;
  private int remotePort;
  InetAddress remoteHostAddress;
  DatagramSocket socket;

  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Wrong number of arguments");
      System.exit(1);
    }
    Draw draw = new Draw();
    try {
      draw.myPort = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.err.println("could not parse myPort");
      System.exit(1);
    }
    try {
      draw.remotePort = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
      System.err.println("could not parse remotePort");
      System.exit(1);
    }
    try {
      draw.remoteHostAddress = InetAddress.getByName(args[1]);
    } catch (UnknownHostException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    try {
      draw.socket = new DatagramSocket(draw.myPort);
    } catch (SocketException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    draw.listen();
  }

  public Draw() {
    paper = new Paper(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().add(paper, BorderLayout.CENTER);

    setSize(640, 480);
    setVisible(true);
  }

  private void sendPacket(Point point) {
    String pointString =
      Double.toString(point.getX()) + "," + Double.toString(point.getY());
    byte[] buffer;
    try {
      buffer = pointString.getBytes(("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      return;
    }
    DatagramPacket packet = new DatagramPacket(
      buffer,
      buffer.length,
      remoteHostAddress,
      remotePort
    );
    try {
      socket.send(packet);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void listen() {
    new Thread(() -> {
      byte[] buffer = new byte[64];
      while (true) {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
          socket.receive(packet);
          String pointString = new String(buffer, 0, buffer.length, "UTF-8");
          String[] pointStrings = pointString.split(",");
          try {
            int x = (int) Double.parseDouble(pointStrings[0]);
            int y = (int) Double.parseDouble(pointStrings[1]);
            paper.remoteAddPoint(new Point(x, y));
          } catch (NullPointerException | NumberFormatException e) {
            System.err.println("Error parsing string: " + e.getMessage());
          }
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      }
    })
      .start();
  }

  @Override
  public void pointAdded(Point point) {
    sendPacket(point);
  }
}

class Paper extends JPanel {

  private HashSet<Point> hashSet = new HashSet<>();
  private AddPointListener listener;

  public Paper(AddPointListener caller) {
    this.listener = caller;
    setBackground(Color.white);
    addMouseListener(new L1());
    addMouseMotionListener(new L2());
  }

  public synchronized void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    graphics.setColor(Color.black);
    Iterator<Point> i = hashSet.iterator();
    while (i.hasNext()) {
      Point point = (Point) i.next();
      graphics.fillOval(point.x, point.y, 2, 2);
    }
  }

  public synchronized void remoteAddPoint(Point point) {
    hashSet.add(point);
    repaint();
  }

  private synchronized void addPoint(Point point) {
    hashSet.add(point);
    repaint();
    listener.pointAdded(point);
  }

  class L1 extends MouseAdapter {

    public void mousePressed(MouseEvent mouseEvent) {
      addPoint(mouseEvent.getPoint());
    }
  }

  class L2 extends MouseMotionAdapter {

    public void mouseDragged(MouseEvent mouseEvent) {
      addPoint(mouseEvent.getPoint());
    }
  }
}
