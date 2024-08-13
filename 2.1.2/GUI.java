import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> messageList;

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

    public void addMessage(String message) {
        listModel.addElement(message);
    }
}
