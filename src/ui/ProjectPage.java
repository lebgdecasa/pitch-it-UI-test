// ui/ProjectPage.java
package ui;

import ui.components.Button;
import ui.components.HamburgerMenu;
import ui.components.Size48;

import domain.models.Pitch;
import application.services.PitchService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Objects;

public class ProjectPage extends JFrame {

    private Pitch pitch;

    public ProjectPage(Pitch pitch) {
        this.pitch = pitch;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Project");
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

        // Project Title (Pitch Name)
        JLabel titleLabel = new JLabel(pitch.getName());
        titleLabel.setFont(new Font("Inter", Font.BOLD, 64));
        titleLabel.setBounds(282, 156, 600, 70);
        mainPanel.add(titleLabel);

        // Image Frame (frame-3.svg placeholder)
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        imagePanel.setBounds(84, 282, 569, 292);
        // If you have a background image, set it here
        mainPanel.add(imagePanel);

        // Description Panel
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setBounds(84, 609, 569, 284);

        JLabel descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        descriptionLabel.setText("<html><b>Description<br></b>" + pitch.getDescription() + "</html>");
        descriptionLabel.setVerticalAlignment(JLabel.TOP);

        descriptionPanel.add(descriptionLabel, BorderLayout.CENTER);
        mainPanel.add(descriptionPanel);

        // Target Audience Panel
        JPanel audiencePanel = new JPanel();
        audiencePanel.setLayout(new BorderLayout());
        audiencePanel.setBounds(720, 282, 405, 292);

        JLabel audienceLabel = new JLabel();
        audienceLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        audienceLabel.setText("<html><b>Target Audience Analysis</b><br>" + pitch.getTargetAudience() + "</html>");
        audienceLabel.setVerticalAlignment(JLabel.TOP);

        audiencePanel.add(audienceLabel, BorderLayout.CENTER);
        mainPanel.add(audiencePanel);

        // Edit Button
        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Inter", Font.PLAIN, 32));
        URL editIconUrl = getClass().getResource("/edit.png");
        if (editIconUrl != null) {
            editButton.setIcon(new ImageIcon(editIconUrl));
        }
        editButton.setBounds(1205, 152, 150, 94);
        mainPanel.add(editButton);

        // View Personas Button
        Button viewPersonasButton = new Button("View Personas");
        viewPersonasButton.setBounds(820, 609, 420, 75);
        mainPanel.add(viewPersonasButton);
        viewPersonasButton.addActionListener((ActionEvent e) -> {
            // Navigate to PersonasListPage
            dispose();
            PersonasListPage personasListPage = new PersonasListPage(pitch);
            personasListPage.setVisible(true);
        });

        // Ask Personalities Button
        Button askPersonalitiesButton = new Button("Ask Personalities");
        askPersonalitiesButton.setBounds(820, 708, 420, 75);
        mainPanel.add(askPersonalitiesButton);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setBounds(821, 812, 420, 75);
        mainPanel.add(backButton);

        // Action Listeners
        editButton.addActionListener((ActionEvent e) -> {
            // Implement edit functionality
            JOptionPane.showMessageDialog(this, "Edit functionality not implemented yet.");
        });

        askPersonalitiesButton.addActionListener((ActionEvent e) -> {
            // Navigate to Ask Personalities Page (to be implemented)
            JOptionPane.showMessageDialog(this, "Ask Personalities functionality not implemented yet.");
        });

        backButton.addActionListener((ActionEvent e) -> {
            // Navigate back to DashboardPage
            dispose();
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.setVisible(true);
        });

        // Add main panel to the frame
        add(mainPanel);
    }
}
