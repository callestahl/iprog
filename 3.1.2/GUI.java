import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * represents the graphical user interface for the guestbook application
 */
public class GUI extends JFrame {

  private DefaultListModel<String> listModel;
  private JList<String> messageList;
  private JTextField nameField;
  private JTextField emailField;
  private JTextField homepageField;
  private JTextField commentField;
  private JButton addButton;
  private InsertGuestbookEntryListener listener;

  /**
   * Constructs a new GUI and adds listener
   *
   * @param listener the listener for inserting guestbook entries
   */
  public GUI(InsertGuestbookEntryListener listener) {
    this.listener = listener;
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    listModel = new DefaultListModel<>();
    messageList = new JList<>(listModel);
    JScrollPane scrollPane = new JScrollPane(messageList);
    add(scrollPane, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BorderLayout());

    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(5, 2));

    // input fields
    gridPanel.add(new JLabel("Name:"));
    nameField = new JTextField();
    gridPanel.add(nameField);

    gridPanel.add(new JLabel("E-mail:"));
    emailField = new JTextField();
    gridPanel.add(emailField);

    gridPanel.add(new JLabel("Homepage:"));
    homepageField = new JTextField();
    gridPanel.add(homepageField);

    gridPanel.add(new JLabel("Comment:"));
    commentField = new JTextField();
    gridPanel.add(commentField);

    // button
    gridPanel.add(new JLabel("Add:"));
    addButton = new JButton("Add");
    gridPanel.add(addButton);

    add(gridPanel, BorderLayout.NORTH);
    add(inputPanel, BorderLayout.SOUTH);

    // handle press on add button by notifying main program
    addButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (noFieldBlank()) {
            listener.insertGuestbookEntry(
              nameField.getText(),
              emailField.getText(),
              homepageField.getText(),
              commentField.getText()
            );
            clearFields();
          }
        }
      }
    );

    setVisible(true);
  }

  /**
   * checks if any of the input fields name, email, homepage or comment are blank
   *
   * @return true if none of the input fields are blank, false otherwise
   */
  private boolean noFieldBlank() {
    return (
      !nameField.getText().isBlank() &&
      !emailField.getText().isBlank() &&
      !homepageField.getText().isBlank() &&
      !commentField.getText().isBlank()
    );
  }

  /**
   * clears the text in all input fields name, email, homepage and comment
   */
  private void clearFields() {
    nameField.setText("");
    emailField.setText("");
    homepageField.setText("");
    commentField.setText("");
  }

  /**
   * updates the list model with all guestbook entries
   *
   * @param allGuestbookEntries the list of all guestbook entries to be displayed
   */
  public void updateEntries(List<DataBaseEntry> allGuestbookEntries) {
    // clear old entries
    listModel.clear();
    // add new entries
    for (DataBaseEntry dataBaseEntry : allGuestbookEntries) {
      listModel.addElement(dataBaseEntry.toString());
    }
  }
}
