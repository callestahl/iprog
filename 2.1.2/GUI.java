import java.awt.*;
import javax.swing.*;

/**
 * class that extends JFrame to create a GUI for server messages.
 */
public class GUI extends JFrame {

  private DefaultListModel<String> listModel;
  private JList<String> messageList;

  /**
   * Constructor to set up the GUI.
   */
  public GUI() {
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    listModel = new DefaultListModel<>();
    messageList = new JList<>(listModel);
    JScrollPane scrollPane = new JScrollPane(messageList);
    add(scrollPane, BorderLayout.CENTER);

    setVisible(true);
  }

  /**
   * adds a message to the list.
   * @param message the message to be added to the list.
   */
  public void addMessage(String message) {
    listModel.addElement(message);
  }
}
