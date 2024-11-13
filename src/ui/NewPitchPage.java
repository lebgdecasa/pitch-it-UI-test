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
import java.net.URL;
import java.util.Objects;

public class NewPitchPage extends JFrame {

    private InputField nameField;
    private TextAreaField descriptionField;

    public NewPitchPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - New Pitch");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with absolute layout
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        // Load logo image
        URL logoUrl = getClass().getResource("/pitch-t-logo.png");
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(logoUrl));
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(93, 17, 383, 135);
        mainPanel.add(logoLabel);

        // HamburgerMenu
        HamburgerMenu hamburgerMenu = new HamburgerMenu();
        hamburgerMenu.setBounds(162, 153, 100, 50); // Adjust size as needed
        mainPanel.add(hamburgerMenu);

        // "New Pitch" Title
        JLabel newPitchTitle = new JLabel("New Pitch");
        newPitchTitle.setFont(new Font("Inter", Font.BOLD, 64));
        newPitchTitle.setBounds(302, 160, 400, 70);
        mainPanel.add(newPitchTitle);

        // "Enter Name:" Label
        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setFont(new Font("Inter", Font.PLAIN, 48));
        nameLabel.setBounds(69, 278, 400, 50);
        mainPanel.add(nameLabel);

        // Name InputField
        nameField = new InputField();
        nameField.setBounds(70, 353, 494, 40);
        mainPanel.add(nameField);

        // "Describe Project:" Label
        JLabel descriptionLabel = new JLabel("Describe Project:");
        descriptionLabel.setFont(new Font("Inter", Font.PLAIN, 48));
        descriptionLabel.setBounds(70, 465, 400, 50);
        mainPanel.add(descriptionLabel);

        // Description TextAreaField
        descriptionField = new TextAreaField();
        descriptionField.setBounds(64, 540, 1270, 248);
        mainPanel.add(descriptionField);

        // "Upload Image" Button
        JButton uploadImageButton = new JButton("Upload Image");
        uploadImageButton.setFont(new Font("Inter", Font.PLAIN, 48));
        uploadImageButton.setBackground(new Color(0xECECEC));
        uploadImageButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uploadImageButton.setFocusPainted(false);
        uploadImageButton.setBounds(832, 337, 400, 80);

        // Add Icon to the button (optional)
        URL iconUrl = getClass().getResource("/paperclip.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            uploadImageButton.setIcon(icon);
        }
        mainPanel.add(uploadImageButton);

        // "Generate Pitch" Button
        JButton generatePitchButton = new JButton("Generate Pitch");
        generatePitchButton.setFont(new Font("Inter", Font.PLAIN, 48));
        generatePitchButton.setBackground(new Color(0xECECEC));
        generatePitchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        generatePitchButton.setFocusPainted(false);
        generatePitchButton.setBounds(248, 850, 420, 75);
        mainPanel.add(generatePitchButton);
        generatePitchButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText();
            String description = descriptionField.getText();

            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Description are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new Pitch object
            Pitch newPitch = new Pitch(name, description, null); // Handle image as needed

            // Add to PitchService
            PitchService pitchService = PitchService.getInstance();
            pitchService.addPitch(newPitch);

            // Navigate to ProjectPage
            dispose();
            ProjectPage projectPage = new ProjectPage(newPitch);
            projectPage.setVisible(true);
        });

        // "Cancel" Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Inter", Font.PLAIN, 48));
        cancelButton.setBackground(new Color(0xECECEC));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(848, 850, 420, 75);
        mainPanel.add(cancelButton);

        // Add action listeners
        generatePitchButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText();
            String description = descriptionField.getText();

            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Description are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new Pitch object
            Pitch newPitch = new Pitch(name, description, null); // Image handling can be added later

            // Add to PitchService
            PitchService pitchService = PitchService.getInstance();
            pitchService.addPitch(newPitch);

            // Navigate back to DashboardPage
            dispose();
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.setVisible(true);
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

        // Add main panel to the frame
        add(mainPanel);
    }
}
