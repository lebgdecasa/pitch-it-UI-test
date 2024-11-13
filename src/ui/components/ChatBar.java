// ui/components/ChatBar.java
package ui.components;

import javax.swing.*;
import java.awt.*;

public class ChatBar extends JPanel {
    private JTextField inputField;
    private JButton sendButton;

    public ChatBar() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 50));

        inputField = new JTextField();
        sendButton = new JButton("Send");

        add(inputField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JButton getSendButton() {
        return sendButton;
    }
}
