/**
 * denna fil bygger på filen från uppgiftsbeskrivningen
 */
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

/**
 * Draw represents a drawing application that can send and receive points with 
 * UDP
 */
public class Draw extends JFrame implements AddPointListener {

  private Paper paper;
  private int myPort;
  private int remotePort;
  InetAddress remoteHostAddress;
  DatagramSocket socket;

  /**
   * the main method. expects three arguments: myPort, remoteHostAddress, and 
   * remotePort
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // handle command line arguments
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

    // create a DatagramSocket
    try {
      draw.socket = new DatagramSocket(draw.myPort);
    } catch (SocketException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    draw.listen();
  }

  /**
   * Constructor for the Draw class
   * initializes the Paper component and sets up the JFrame
   */
  public Draw() {
    paper = new Paper(this);
    // set the default close operation to exit the application
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().add(paper, BorderLayout.CENTER);

    setSize(640, 480);
    setVisible(true);
  }

  /**
   * sends a packet containing the coordinates of the given point to the remote 
   * host.
   *
   * @param point the Point object to be sent
   */
  private void sendPacket(Point point) {
    // convert the point coordinates to a comma separated string
    String pointString =
      Double.toString(point.getX()) + "," + Double.toString(point.getY());
    byte[] buffer;
    try {
      // convert the string to a byte array
      buffer = pointString.getBytes(("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      return;
    }
    // create a DatagramPacket with the data
    DatagramPacket packet = new DatagramPacket(
      buffer,
      buffer.length,
      remoteHostAddress,
      remotePort
    );
    // send the packet
    try {
      socket.send(packet);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * starts a new thread to listen for incoming packets and add points to the
   * Paper component.
   */
  private void listen() {
    new Thread(() -> {
      // to store incoming data
      byte[] buffer = new byte[64];
      while (true) {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
          socket.receive(packet);
          // convert received data to string
          String pointString = new String(buffer, 0, buffer.length, "UTF-8");
          // split into x and y
          String[] pointStrings = pointString.split(",");
          try {
            // parse coordinates
            int x = (int) Double.parseDouble(pointStrings[0]);
            int y = (int) Double.parseDouble(pointStrings[1]);
            // add the received point
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

/**
 * represents a drawing surface
 */
class Paper extends JPanel {

  private HashSet<Point> hashSet = new HashSet<>();
  private AddPointListener listener;

  /**
   * constructor for the Paper class
   * sets background color, sets up mouse listeners and adds AddPointListener
   *
   * @param caller the AddPointListener that will be notified when a point is
   * added
   */
  public Paper(AddPointListener caller) {
    this.listener = caller;
    setBackground(Color.white);
    addMouseListener(new L1());
    addMouseMotionListener(new L2());
  }

  /**
   * paints the component by drawing all points in the hashSet
   *
   * @param graphics the Graphics object used for drawing
   */
  public synchronized void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    graphics.setColor(Color.black);
    Iterator<Point> i = hashSet.iterator();
    while (i.hasNext()) {
      Point point = (Point) i.next();
      graphics.fillOval(point.x, point.y, 2, 2);
    }
  }

  /**
   * adds a point received from a remote source to the hashSet and repaints the
   * component.
   *
   * @param point the Point object to be added
   */
  public synchronized void remoteAddPoint(Point point) {
    hashSet.add(point);
    repaint();
  }

  /**
   * adds a point to the hashSet, repaints the component add notify listener
   *
   * @param point the Point object to be added
   */
  private synchronized void addPoint(Point point) {
    hashSet.add(point);
    repaint();
    listener.pointAdded(point);
  }

  /**
   * adds a point when the mouse is pressed
   */
  class L1 extends MouseAdapter {

    /**
     * called when the mouse is pressed
     *
     * @param mouseEvent the event object
     */
    public void mousePressed(MouseEvent mouseEvent) {
      addPoint(mouseEvent.getPoint());
    }
  }

  /**
   * adds a point when the mouse is dragged
   */
  class L2 extends MouseMotionAdapter {

    /**
     * called when the mouse is dragged
     *
     * @param mouseEvent the event object
     */
    public void mouseDragged(MouseEvent mouseEvent) {
      addPoint(mouseEvent.getPoint());
    }
  }
}
