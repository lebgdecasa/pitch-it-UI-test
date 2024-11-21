// ui/PersonaChatPage.java
package ui;

import application.services.chatgptapi;
import domain.models.ChatMessage;
import ui.components.HamburgerMenu;
import ui.components.Button;
import domain.models.Persona;
import domain.models.Pitch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaChatPage extends JFrame {

    private Persona persona;
    private Pitch currentPitch;
    private JTextArea chatTextArea;
    private JTextField messageField;

    public PersonaChatPage(Persona persona, Pitch currentPitch) {
        this.persona = persona;
        this.currentPitch = currentPitch;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Chat with " + persona.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Set main panel as content pane
        setContentPane(mainPanel);

        // Build the UI components
        createHeader(mainPanel);
        createChatArea(mainPanel);
        createFooter(mainPanel);
    }

    private void createHeader(JPanel mainPanel) {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left Side (Hamburger Menu and Logo)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(Color.WHITE);

        // Hamburger Menu
        HamburgerMenu hamburgerMenu = new HamburgerMenu() {
            @Override
            protected void navigateToDashboard() {
                dispose();
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.setVisible(true);
            }

            @Override
            protected void navigateToNewPitch() {
                dispose();
                NewPitchPage newPitchPage = new NewPitchPage();
                newPitchPage.setVisible(true);
            }

            @Override
            protected void navigateToPersonalities() {
                dispose();
                PersonalitiesPage personalitiesPage = new PersonalitiesPage();
                personalitiesPage.setVisible(true);
            }

            @Override
            protected void navigateToAccountSettings() {
                dispose();
                AccountSettingsPage accountSettingsPage = new AccountSettingsPage();
                accountSettingsPage.setVisible(true);
            }

        };
        leftPanel.add(hamburgerMenu);

        // Logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/pitch-t-logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        leftPanel.add(logoLabel);

        headerPanel.add(leftPanel, BorderLayout.WEST);

        // Center Panel (Persona's Name and Avatar)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        centerPanel.setBackground(Color.WHITE);

        // Persona's Avatar
        ImageIcon avatarIcon = scaleImageIcon(persona.getAvatar(), 64, 64);
        JLabel avatarLabel = new JLabel(avatarIcon);
        centerPanel.add(avatarLabel);

        // Persona's Name
        JLabel nameLabel = new JLabel(persona.getName());
        nameLabel.setFont(new Font("Inter", Font.PLAIN, 32));
        centerPanel.add(nameLabel);

        headerPanel.add(centerPanel, BorderLayout.CENTER);

        // Add header panel to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createChatArea(JPanel mainPanel) {
        // Chat Area Panel
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(Color.WHITE);

        // Chat Text Area
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatTextArea.setWrapStyleWord(true);
        chatTextArea.setFont(new Font("Inter", Font.PLAIN, 16));

        // Scroll Pane for Chat Text Area
        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        // Add chat panel to main panel
        mainPanel.add(chatPanel, BorderLayout.CENTER);
    }

    private void createFooter(JPanel mainPanel) {
        // Footer Panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Attach Button
        JButton attachButton = new JButton();
        attachButton.setPreferredSize(new Dimension(40, 40));
        ImageIcon attachIcon = new ImageIcon(getClass().getResource("/attach.png")); // Replace with your attach icon
        if (attachIcon != null) {
            attachButton.setIcon(scaleImageIcon(attachIcon, 24, 24));
        } else {
            attachButton.setText("+");
            attachButton.setFont(new Font("Inter", Font.BOLD, 24));
        }

        // Message Input Field
        messageField = new JTextField();
        messageField.setFont(new Font("Inter", Font.PLAIN, 16));

        // Send Button
        JButton sendButton = new JButton();
        sendButton.setPreferredSize(new Dimension(40, 40));
        ImageIcon sendIcon = new ImageIcon(getClass().getResource("/send.png")); // Replace with your send icon
        if (sendIcon != null) {
            sendButton.setIcon(scaleImageIcon(sendIcon, 24, 24));
        } else {
            sendButton.setText(">");
            sendButton.setFont(new Font("Inter", Font.BOLD, 24));
        }

        // Add action listener to send message
        sendButton.addActionListener(e -> sendMessage());

        // Add action listener for Enter key
        messageField.addActionListener(e -> sendMessage());

        // Left Panel for Attach Button
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(attachButton, BorderLayout.CENTER);

        // Right Panel for Send Button
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(sendButton, BorderLayout.CENTER);

        // Center Panel for Message Field
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(messageField, BorderLayout.CENTER);

        // Assemble Footer Panel
        footerPanel.add(leftPanel, BorderLayout.WEST);
        footerPanel.add(centerPanel, BorderLayout.CENTER);
        footerPanel.add(rightPanel, BorderLayout.EAST);

        // Add footer panel to main panel
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            // Display user's message in chat area
            chatTextArea.append("You: " + message + "\n");
            messageField.setText("");

            // Disable input during processing
            messageField.setEnabled(false);

            // Use SwingWorker for asynchronous processing
            SwingWorker<String, Void> worker = new SwingWorker<>() {
                @Override
                protected String doInBackground() throws Exception {
                    // Prepare messages for GPT API
                    List<ChatMessage> messages = new ArrayList<>();
                    String systemMessageContent = "You are " + persona.getName() + ", a persona with the following characteristics:\n" +
                            "Age: " + persona.getAge() + "\n" +
                            "Occupation: " + persona.getOccupation() + "\n" +
                            "Interests: " + persona.getInterests() + "\n" +
                            "Goals: " + persona.getStats() + "\n" +
                            "Pain Points: " + persona.getAbout() + "\n" +
                            "Respond to the user about" + currentPitch.getDescription() + "accordingly";

                    messages.add(new ChatMessage("system", systemMessageContent));
                    messages.add(new ChatMessage("user", message));

                    // Get response from GPT API
                    String response = chatgptapi.getResponse(messages);
                    return response;
                }

                @Override
                protected void done() {
                    try {
                        String response = get();
                        // Display persona's response
                        chatTextArea.append(persona.getName() + ": " + response + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                        chatTextArea.append(persona.getName() + ": " + "Sorry, I couldn't process your request.\n");
                    } finally {
                        // Re-enable input
                        messageField.setEnabled(true);
                        messageField.requestFocus();
                    }
                }
            };

            worker.execute();
        }
    }

    // Utility method to scale images
    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
