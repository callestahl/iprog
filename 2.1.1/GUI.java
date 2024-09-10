import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * GUI class that represents a simple chat client.
 * Implements the MessageObservable interface to notify Client when a message 
 * is sent.
 */
public class GUI extends JFrame implements MessageObservable {

  private List<MessageObserver> observers = new ArrayList<MessageObserver>();

  private DefaultListModel<String> listModel;
  private JList<String> messageList;
  private JTextField inputField;
  private JButton sendButton;

  /**
   * Constructs the GUI and initializes its components.
   */
  public GUI() {
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // messages
    listModel = new DefaultListModel<>();
    messageList = new JList<>(listModel);
    JScrollPane scrollPane = new JScrollPane(messageList);
    add(scrollPane, BorderLayout.CENTER);

    // input
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BorderLayout());
    inputField = new JTextField();
    sendButton = new JButton("Send");
    inputPanel.add(inputField, BorderLayout.CENTER);
    inputPanel.add(sendButton, BorderLayout.EAST);
    add(inputPanel, BorderLayout.SOUTH);

    // handle send button click
    sendButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String message = inputField.getText();
          if (!message.isEmpty()) {
            notifyObservers();
            inputField.setText("");
          }
        }
      }
    );

    setVisible(true);
  }

  /**
   * Adds a message to the message list.
   *
   * @param message the message to be added
   */
  public void addMessage(String message) {
    listModel.addElement(message);
  }

  @Override
  public void notifyObservers() {
    for (MessageObserver observer : observers) {
      observer.messageSent(inputField.getText());
    }
  }

  @Override
  public void addObserver(MessageObserver observer) {
    observers.add(observer);
  }
}
