// ui/VisionPage.java
package ui;

import application.services.PersonaService;
import ui.components.Button;
import ui.components.HamburgerMenu;
import domain.models.Persona;
import domain.models.Pitch;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VisionPage extends JFrame {

    private final Persona persona;
    private final Pitch currentPitch;
    private final boolean isFromPersonaPage;

    public VisionPage(Persona persona, Pitch pitch, boolean isFromPersonaPage) {
        this.persona = persona;
        this.currentPitch = pitch;
        this.isFromPersonaPage = isFromPersonaPage;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Vision for " + currentPitch.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Build the UI components
        createHeader(mainPanel);
        createCenterPanel(mainPanel);
        createFooter(mainPanel);
    }

    private void createHeader(JPanel mainPanel) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left: Hamburger Menu and Logo
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(Color.WHITE);

        HamburgerMenu hamburgerMenu = new HamburgerMenu() {
            @Override
            protected void navigateToDashboard() {
                dispose();
                new DashboardPage().setVisible(true);
            }

            @Override
            protected void navigateToNewPitch() {
                dispose();
                new NewPitchPage().setVisible(true);
            }

            @Override
            protected void navigateToPersonalities() {
                dispose();
                new PersonalitiesPage().setVisible(true);
            }

            @Override
            protected void navigateToAccountSettings() {
                dispose();
                new AccountSettingsPage().setVisible(true);
            }
        };
        leftPanel.add(hamburgerMenu);

        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pitch-t-logo.png")));
        JLabel logoLabel = new JLabel(logoIcon);
        leftPanel.add(logoLabel);

        headerPanel.add(leftPanel, BorderLayout.WEST);

        // Center: Project Name and Persona Name
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(Color.WHITE);

        JLabel projectLabel = new JLabel(currentPitch.getName(), SwingConstants.CENTER);
        projectLabel.setFont(new Font("Inter", Font.BOLD, 32));
        centerPanel.add(projectLabel);

        JLabel personaLabel = new JLabel("Persona: " + persona.getName(), SwingConstants.CENTER);
        personaLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        centerPanel.add(personaLabel);

        headerPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel(JPanel mainPanel) {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left: Placeholder for Generated AD
        JPanel adPanel = new JPanel(new BorderLayout());
        adPanel.setBackground(Color.WHITE);
        adPanel.setBorder(BorderFactory.createTitledBorder("Generated AD"));

        JLabel adPlaceholder = new JLabel("AI-Generated AD Placeholder", SwingConstants.CENTER);
        adPlaceholder.setFont(new Font("Inter", Font.PLAIN, 16));
        adPanel.add(adPlaceholder, BorderLayout.CENTER);

        centerPanel.add(adPanel);

        // Right: Chatbox Panel
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(Color.WHITE);
        chatPanel.setBorder(BorderFactory.createTitledBorder("Chat with Persona"));

        JTextArea chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatTextArea.setWrapStyleWord(true);
        chatTextArea.setFont(new Font("Inter", Font.PLAIN, 16));

        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JTextField messageField = new JTextField();
        messageField.setFont(new Font("Inter", Font.PLAIN, 16));
        chatPanel.add(messageField, BorderLayout.SOUTH);

        centerPanel.add(chatPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void createFooter(JPanel mainPanel) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(Color.WHITE);

        // Back Button
        Button backButton = new Button("Back");
        backButton.addActionListener(e -> {
            // Close the current VisionPage window
            dispose();

            // Navigate to the appropriate page based on the context
            if (isFromPersonaPage) {
                // Reopen the PersonaPage
                JFrame personaFrame = new JFrame("Persona Details");
                personaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                personaFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                personaFrame.setLocationRelativeTo(null);

                PersonaPage personaPage = new PersonaPage(persona, currentPitch, true);
                personaFrame.setContentPane(personaPage);
                personaFrame.setVisible(true);
            } else {
                // Reopen the PersonasListPage
                PersonasListPage personasListPage = new PersonasListPage(
                        PersonaService.getInstance().getPersonasForPitch(currentPitch),
                        currentPitch
                );
                personasListPage.setVisible(true);
            }
        });
        footerPanel.add(backButton);

        // Regenerate Button
        Button regenerateButton = new Button("Regenerate");
        regenerateButton.addActionListener(e -> {
            // Open a dialog for regenerating AD
            String userInput = JOptionPane.showInputDialog(
                    this,
                    "Enter modifications for the AD prompt:",
                    "Regenerate AD",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (userInput != null && !userInput.trim().isEmpty()) {
                // Placeholder logic for regenerating AD
                JOptionPane.showMessageDialog(this, "AD regenerated with input: " + userInput);
            }
        });
        footerPanel.add(regenerateButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }
}
