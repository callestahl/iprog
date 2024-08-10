import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame implements MessageObservable {
    private List<MessageObserver> observers = new ArrayList<MessageObserver>();

    private DefaultListModel<String> listModel;
    private JList<String> messageList;
    private JTextField inputField;
    private JButton sendButton;

    public GUI() {
        setTitle("Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        messageList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(messageList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputField = new JTextField();
        sendButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    notifyObservers();
                    inputField.setText("");
                }
            }
        });
    }

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
