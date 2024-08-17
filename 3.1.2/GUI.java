import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class GUI extends JFrame {

  private DefaultListModel<String> listModel;
  private JList<String> messageList;
  private JTextField nameField;
  private JTextField emailField;
  private JTextField homepageField;
  private JTextField commentField;
  private JButton addButton;

  public GUI() {
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

    gridPanel.add(new JLabel("Add:"));
    addButton = new JButton("Add");
    gridPanel.add(addButton);

    add(gridPanel, BorderLayout.NORTH);
    add(inputPanel, BorderLayout.SOUTH);

    listModel.addElement("<html>namegsdgdgs<br>bbkdkbdfjbdjfbfj<br>sdggsgsddg</html>");

    addButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {}
      }
    );

    setVisible(true);
  }
}

class DataBaseEntry {}
