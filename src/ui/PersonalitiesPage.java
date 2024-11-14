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
import java.util.ArrayList;

public class PersonalitiesPage extends JFrame {

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private ChatBar chatBar;
    private ChatService chatService;
    private Personality selectedPersonality;
    private JTextArea chatArea;
    private DefaultListModel<String> chatHistoryModel;
    private JLabel avatarLabel;
    private JLabel nameLabel;

    public PersonalitiesPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Pitch!t - Personalities");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // HamburgerMenu
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
                // Already on PersonalitiesPage
            }

            @Override
            protected void navigateToAccountSettings() {
                dispose();
                AccountSettingsPage accountSettingsPage = new AccountSettingsPage();
                accountSettingsPage.setVisible(true);
            }

        };
        headerPanel.add(hamburgerMenu, BorderLayout.WEST);

        // Title Label
        JLabel titleLabel = new JLabel("Personalities");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Left Panel
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(300, 0)); // Fixed width
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2; // Adjust as needed
        contentPanel.add(leftPanel, gbc);

        // Center Panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        contentPanel.add(centerPanel, gbc);

        // Right Panel
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setPreferredSize(new Dimension(300, 0)); // Fixed width
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        contentPanel.add(rightPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Left Panel Components
        initializeLeftPanel();

        // Center Panel Components
        initializeCenterPanel();

        // Right Panel Components
        initializeRightPanel();

        // Add main panel to frame
        setContentPane(mainPanel);

        // Initialize Chat Service
        chatService = new ChatService();
    }

    private void initializeLeftPanel() {
        // Left Panel Layout
        leftPanel.setLayout(new BorderLayout());

        // Personalities Label
        JLabel personalitiesLabel = new JLabel("Personalities");
        personalitiesLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        personalitiesLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(personalitiesLabel, BorderLayout.NORTH);

        // Personalities List Panel
        JPanel personalitiesListPanel = new JPanel();
        personalitiesListPanel.setLayout(new BoxLayout(personalitiesListPanel, BoxLayout.Y_AXIS));
        personalitiesListPanel.setBackground(Color.LIGHT_GRAY);

        // Fetch personalities
        PersonalityService personalityService = PersonalityService.getInstance();
        List<Personality> personalities = personalityService.getAllPersonalities();

        for (Personality personality : personalities) {
            PersonalityBox personalityBox = new PersonalityBox(personality);

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

            personalitiesListPanel.add(personalityBox);
        }

        // Scroll Pane for Personalities List
        JScrollPane personalitiesScrollPane = new JScrollPane(personalitiesListPanel);
        leftPanel.add(personalitiesScrollPane, BorderLayout.CENTER);
    }

    private void initializeCenterPanel() {
        // Center Panel Layout
        centerPanel.setLayout(new BorderLayout());

        // Personality Header
        JPanel personalityHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        personalityHeader.setBackground(Color.WHITE);

        // Avatar Label
        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(64, 64));
        personalityHeader.add(avatarLabel);

        // Name Label
        nameLabel = new JLabel("Select a Personality");
        nameLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        personalityHeader.add(nameLabel);

        centerPanel.add(personalityHeader, BorderLayout.NORTH);

        // Chat Area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        centerPanel.add(chatScrollPane, BorderLayout.CENTER);

        // Chat Bar
        chatBar = new ChatBar();
        centerPanel.add(chatBar, BorderLayout.SOUTH);

        // Add action listener to send button
        chatBar.getSendButton().addActionListener(e -> {
            sendMessage(chatBar.getInputField().getText());
            chatBar.getInputField().setText("");
        });
    }

    private void initializeRightPanel() {
        // Right Panel Layout
        rightPanel.setLayout(new BorderLayout());

        // Chat History Label
        JLabel chatHistoryLabel = new JLabel("Chat History");
        chatHistoryLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        chatHistoryLabel.setHorizontalAlignment(JLabel.CENTER);
        rightPanel.add(chatHistoryLabel, BorderLayout.NORTH);

        // Chat History List
        chatHistoryModel = new DefaultListModel<>();
        JList<String> chatHistoryList = new JList<>(chatHistoryModel);
        JScrollPane chatHistoryScrollPane = new JScrollPane(chatHistoryList);
        rightPanel.add(chatHistoryScrollPane, BorderLayout.CENTER);
    }

    private void selectPersonality(Personality personality) {
        this.selectedPersonality = personality;
        // Update UI to show selected personality
        // Update avatar and name
        avatarLabel.setIcon(scaleImageIcon(personality.getAvatar(), 64, 64));
        nameLabel.setText(personality.getName());

        // Clear chat area and chat history
        chatArea.setText("");
        chatHistoryModel.clear();
        chatService = new ChatService(); // Reset chat service
    }

    private void sendMessage(String messageText) {
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
        chatHistoryModel.addElement("You: " + messageText);
    }

    // Utility method to scale images
    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
