// ui/PersonalitiesPage.java
package ui;

import ui.components.PersonalityBox;
import ui.components.ChatBar;
import ui.components.HamburgerMenu;
import domain.models.Personality;
import domain.models.ChatMessage;
import application.services.PersonalityService;
import application.services.ChatService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PersonalitiesPage extends JFrame {

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private ChatBar chatBar;
    private ChatService chatService;
    private Personality selectedPersonality;
    private JTextArea chatArea;
    private DefaultListModel<String> chatHistoryModel;

    public PersonalitiesPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Pitch!t - Personalities");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        // HamburgerMenu
        HamburgerMenu hamburgerMenu = new HamburgerMenu();
        hamburgerMenu.setBounds(12, 12, 26, 26);
        mainPanel.add(hamburgerMenu);

        // Left Panel
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setBounds(8, 50, 286, 950);

        JLabel personalitiesLabel = new JLabel("Personalities");
        personalitiesLabel.setFont(new Font("Inter", Font.PLAIN, 18));
        personalitiesLabel.setBounds(100, 10, 100, 30);
        leftPanel.add(personalitiesLabel);

        // Fetch personalities
        PersonalityService personalityService = PersonalityService.getInstance();
        List<Personality> personalities = personalityService.getAllPersonalities();

        int yPosition = 50;
        for (Personality personality : personalities) {
            PersonalityBox personalityBox = new PersonalityBox(personality);
            personalityBox.setBounds(0, yPosition, 286, 100);

            personalityBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectPersonality(personality);
                }
            });

            personalityBox.addInfoButtonListener(e -> {
                // Show info popup
                JOptionPane.showMessageDialog(
                        PersonalitiesPage.this,
                        personality.getDescription(),
                        personality.getName(),
                        JOptionPane.INFORMATION_MESSAGE
                );
            });

            leftPanel.add(personalityBox);
            yPosition += 110;
        }

        mainPanel.add(leftPanel);

        // Center Panel
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBounds(300, 0, 840, 1024);

        // Top: Personality Name and Avatar
        JPanel personalityHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        personalityHeader.setBackground(Color.WHITE);
        personalityHeader.setPreferredSize(new Dimension(840, 80));

        JLabel avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(64, 64));
        personalityHeader.add(avatarLabel);

        JLabel nameLabel = new JLabel("Name of Personality");
        nameLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        personalityHeader.add(nameLabel);

        centerPanel.add(personalityHeader, BorderLayout.NORTH);

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        centerPanel.add(chatScrollPane, BorderLayout.CENTER);

        // Chat Bar
        chatBar = new ChatBar();
        centerPanel.add(chatBar, BorderLayout.SOUTH);

        mainPanel.add(centerPanel);

        // Right Panel
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setBounds(1150, 0, 286, 1024);

        JLabel chatHistoryLabel = new JLabel("CHAT HISTORY");
        chatHistoryLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        chatHistoryLabel.setBounds(50, 20, 200, 30);
        rightPanel.add(chatHistoryLabel);

        // Chat history list
        chatHistoryModel = new DefaultListModel<>();
        JList<String> chatHistoryList = new JList<>(chatHistoryModel);
        JScrollPane chatHistoryScrollPane = new JScrollPane(chatHistoryList);
        chatHistoryScrollPane.setBounds(10, 60, 266, 900);
        rightPanel.add(chatHistoryScrollPane);

        mainPanel.add(rightPanel);

        // Add main panel to frame
        add(mainPanel);

        // Initialize Chat Service
        chatService = new ChatService();

        // Add action listener to send button
        chatBar.getSendButton().addActionListener(e -> {
            sendMessage(chatBar.getInputField().getText(), avatarLabel, nameLabel);
            chatBar.getInputField().setText("");
        });
    }

    private void selectPersonality(Personality personality) {
        this.selectedPersonality = personality;
        // Update UI to show selected personality
        // Update avatar and name
        JLabel avatarLabel = (JLabel) ((JPanel) centerPanel.getComponent(0)).getComponent(0);
        JLabel nameLabel = (JLabel) ((JPanel) centerPanel.getComponent(0)).getComponent(1);

        avatarLabel.setIcon(personality.getAvatar());
        nameLabel.setText(personality.getName());

        // Clear chat area and chat history
        chatArea.setText("");
        chatService = new ChatService(); // Reset chat service
    }

    private void sendMessage(String messageText, JLabel avatarLabel, JLabel nameLabel) {
        if (selectedPersonality == null) {
            JOptionPane.showMessageDialog(this, "Please select a personality to chat with.", "No Personality Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (messageText.trim().isEmpty()) {
            return;
        }

        // Add user's message to chat area
        chatArea.append("You: " + messageText + "\n");

        // Send message and get response
        ChatMessage responseMessage = chatService.sendMessage(messageText, selectedPersonality.getName());

        // Add personality's response to chat area
        chatArea.append(selectedPersonality.getName() + ": " + responseMessage.getMessage() + "\n");

        // Update chat history
        chatHistoryModel.addElement(messageText);
    }
}
