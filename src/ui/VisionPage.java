package ui;

import application.services.ImageAnalyzer;
import application.services.PersonaService;
import domain.models.Persona;
import domain.models.Pitch;
import ui.components.Button;
import ui.components.HamburgerMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class VisionPage extends JFrame {

    public static Persona persona = null;
    private final Pitch currentPitch;
    private final boolean isFromPersonaPage;

    // Make adLabel a class-level variable
    private JLabel adLabel;

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

        // Left: Panel for Generated AD
        JPanel adPanel = new JPanel(new BorderLayout());
        adPanel.setBackground(Color.WHITE);
        adPanel.setBorder(BorderFactory.createTitledBorder("Generated AD"));

        // Use the class-level adLabel
        adLabel = new JLabel("Generating AI-Generated AD...", SwingConstants.CENTER);
        adLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        adPanel.add(adLabel, BorderLayout.CENTER);
        centerPanel.add(adPanel);

        // Generate and Display Image
        SwingWorker<Void, Void> imageWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    // Generate and download the image
                    String userInput = "An ad for " + currentPitch.getName() + " based on persona " + persona.getName();
                    String filePath = "generated_ad.png"; // Local file to save the image
                    ImageAnalyzer.generateAndDownloadImage(userInput, filePath);

                    // Load the downloaded image into an ImageIcon
                    File imageFile = new File(filePath);
                    if (imageFile.exists()) {
                        ImageIcon imageIcon = new ImageIcon(filePath);
                        Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

                        // Update UI on the Event Dispatch Thread
                        SwingUtilities.invokeLater(() -> {
                            adLabel.setIcon(new ImageIcon(image));
                            adLabel.setText(null); // Remove placeholder text
                        });
                    } else {
                        throw new Exception("Image file not found after download.");
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> adLabel.setText("Failed to generate the AD. Please try again."));
                    e.printStackTrace(); // Log the error for debugging
                }
                return null;
            }
        };
        imageWorker.execute();

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
            dispose();
            if (isFromPersonaPage) {
                new PersonaPage(persona, currentPitch, true).setVisible(true);
            } else {
                new PersonasListPage(PersonaService.getInstance().getPersonasForPitch(currentPitch), currentPitch).setVisible(true);
            }
        });
        footerPanel.add(backButton);

        // Regenerate Button
        Button regenerateButton = new Button("Regenerate");
        regenerateButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Regenerating AD...");

            // Reuse the original prompt for regeneration
            String originalUserInput = "An ad for " + currentPitch.getName() + " based on persona " + persona.getName();

            // Trigger regeneration logic
            SwingWorker<Void, Void> regenerateWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        String filePath = "regenerated_ad.png"; // File path for regenerated image
                        ImageAnalyzer.generateAndDownloadImage(originalUserInput, filePath);

                        // Update the adLabel with the regenerated image
                        File imageFile = new File(filePath);
                        if (imageFile.exists()) {
                            ImageIcon imageIcon = new ImageIcon(filePath);
                            Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

                            SwingUtilities.invokeLater(() -> {
                                adLabel.setIcon(new ImageIcon(image));
                                adLabel.setText(null); // Clear placeholder text
                            });
                        } else {
                            throw new Exception("Image file not found after regeneration.");
                        }
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                VisionPage.this,
                                "Failed to regenerate AD. Please try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        ));
                        ex.printStackTrace(); // Log error
                    }
                    return null;
                }
            };
            regenerateWorker.execute();
        });
        footerPanel.add(regenerateButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

}
