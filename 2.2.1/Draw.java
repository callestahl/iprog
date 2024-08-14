import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// baserat på exemplet från uppgiftsbeskkrivningen
public class Draw extends JFrame implements AddPointCallback {

  private Paper paper;
  private int myPort;
  private int remotePort;
  private String remoteHost;

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
    draw.remoteHost = args[1];
  }

  public Draw() {
    paper = new Paper(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().add(paper, BorderLayout.CENTER);

    setSize(640, 480);
    setVisible(true);
  }

  private void sendPacket(Point point) {

  }

  private void listen() {

  }

  @Override
  public void pointAdded(Point point) {
    sendPacket(point);
  }
}

class Paper extends JPanel {
  private HashSet<Point> hashSet = new HashSet<>();
  private AddPointCallback caller;

  public Paper(AddPointCallback caller) {
    this.caller = caller;
    setBackground(Color.white);
    addMouseListener(new L1());
    addMouseMotionListener(new L2());
  }

  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    graphics.setColor(Color.black);
    Iterator<Point> i = hashSet.iterator();
    while (i.hasNext()) {
      Point point = (Point) i.next();
      graphics.fillOval(point.x, point.y, 2, 2);
    }
  }

  private void addPoint(Point point) {
    hashSet.add(point);
    repaint();
    caller.pointAdded(point);
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
