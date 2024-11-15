// ui/NewPitchPage.java
package ui;

import application.services.PitchService;
import domain.models.Pitch;
import ui.components.InputField;
import ui.components.TextAreaField;
import ui.components.HamburgerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewPitchPage extends JFrame {

    private InputField nameField;
    private TextAreaField descriptionField;

    public NewPitchPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - New Pitch");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Logo Panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);

        // Load logo image
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/pitch-t-logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

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
                // Already on NewPitchPage
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
        headerPanel.add(hamburgerMenu, BorderLayout.EAST);
        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Title Label
        JLabel titleLabel = new JLabel("New Pitch");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // "Enter Name:" Label
        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        contentPanel.add(nameLabel, gbc);

        // Name InputField
        nameField = new InputField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        contentPanel.add(nameField, gbc);

        // "Describe Project:" Label
        JLabel descriptionLabel = new JLabel("Describe Project:");
        descriptionLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        contentPanel.add(descriptionLabel, gbc);

        // Description TextAreaField
        descriptionField = new TextAreaField();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(descriptionField, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        // "Upload Image" Button
        JButton uploadImageButton = new JButton("Upload Image");
        uploadImageButton.setFont(new Font("Inter", Font.PLAIN, 24));
        uploadImageButton.setBackground(new Color(0xECECEC));
        uploadImageButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uploadImageButton.setFocusPainted(false);

        // Add Icon to the button (optional)
        ImageIcon icon = new ImageIcon(getClass().getResource("/paperclip.png"));
        if (icon != null) {
            uploadImageButton.setIcon(icon);
        }
        buttonPanel.add(uploadImageButton);

        // "Generate Pitch" Button
        JButton generatePitchButton = new JButton("Generate Pitch");
        generatePitchButton.setFont(new Font("Inter", Font.PLAIN, 24));
        generatePitchButton.setBackground(new Color(0xECECEC));
        generatePitchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        generatePitchButton.setFocusPainted(false);
        buttonPanel.add(generatePitchButton);

        // "Cancel" Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Inter", Font.PLAIN, 24));
        cancelButton.setBackground(new Color(0xECECEC));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setFocusPainted(false);
        buttonPanel.add(cancelButton);

        // Add action listeners
        generatePitchButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText();
            String description = descriptionField.getText();

            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Description are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new Pitch object
            Pitch newPitch = null; // Image handling can be added later
            try {
                newPitch = new Pitch(name, description, null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            // Add to PitchService
            PitchService pitchService = PitchService.getInstance();
            pitchService.addPitch(newPitch);

            // Navigate to ProjectPage
            dispose();
            ProjectPage projectPage = new ProjectPage(newPitch);
            projectPage.setVisible(true);
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            // Return to DashboardPage
            dispose();
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.setVisible(true);
        });

        uploadImageButton.addActionListener((ActionEvent e) -> {
            // Handle image upload
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get selected file
                java.io.File selectedFile = fileChooser.getSelectedFile();
                // TODO: Process the selected file (e.g., store the image path)
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            }
        });

        // Add content and button panels to the main panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(buttonPanel, gbc);

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }
}
