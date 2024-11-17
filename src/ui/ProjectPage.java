// ui/ProjectPage.java
package ui;

import application.services.PersonaService;
import domain.models.Persona;
import ui.components.Button;
import ui.components.HamburgerMenu;
import domain.models.Pitch;
import application.services.PitchService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class ProjectPage extends JFrame {

    private Pitch pitch;

    public ProjectPage(Pitch pitch) {
        this.pitch = pitch;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Project");
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

        // Title Label
        JLabel titleLabel = new JLabel(pitch.getName());
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

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

        // Add components to header panel
        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(hamburgerMenu, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.LIGHT_GRAY);
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // If you have an image for the pitch, you can display it here
        // For now, we'll use a placeholder
        JLabel imageLabel = new JLabel("Image Placeholder", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        imagePanel.add(imageLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        contentPanel.add(imagePanel, gbc);

        // Description Panel
        JTextArea descriptionTextArea = new JTextArea("Description\n" + pitch.getDescription());
        descriptionTextArea.setFont(new Font("Inter", Font.PLAIN, 18));
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weighty = 0.5;
        contentPanel.add(descriptionScrollPane, gbc);

        // Target Audience Panel
        JTextArea audienceTextArea = new JTextArea("Target Audience Analysis: \n" + pitch.getTargetAudience());
        audienceTextArea.setFont(new Font("Inter", Font.PLAIN, 18));
        audienceTextArea.setLineWrap(true);
        audienceTextArea.setWrapStyleWord(true);
        audienceTextArea.setEditable(false);
        JScrollPane audienceScrollPane = new JScrollPane(audienceTextArea);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        contentPanel.add(audienceScrollPane, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(Color.WHITE);

        // Edit Button
        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Inter", Font.PLAIN, 24));
        ImageIcon editIcon = new ImageIcon(getClass().getResource("/edit.png"));
        if (editIcon != null) {
            editButton.setIcon(editIcon);
        }
        footerPanel.add(editButton);

        // View Personas Button
        Button viewPersonasButton = new Button("View Personas");
        footerPanel.add(viewPersonasButton);

        // Ask Personalities Button
        Button askPersonalitiesButton = new Button("Ask Personalities");
        footerPanel.add(askPersonalitiesButton);

        // Back Button
        Button backButton = new Button("Back");
        footerPanel.add(backButton);

        // Action Listeners
        editButton.addActionListener((ActionEvent e) -> {
            // Implement edit functionality
            JOptionPane.showMessageDialog(this, "Edit functionality not implemented yet.");
        });

        viewPersonasButton.addActionListener((ActionEvent e) -> {
            String targetAudience = pitch.getTargetAudience();
            String projectDescription = pitch.getDescription();

            List<Persona> generatedPersonas = null;
            try {
                generatedPersonas = PersonaService.generatePersonas(targetAudience, projectDescription);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generating personas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            // Navigate to PersonasListPage
            dispose();
            PersonasListPage personasListPage = new PersonasListPage(generatedPersonas, pitch);
            personasListPage.setVisible(true);
        });

        askPersonalitiesButton.addActionListener((ActionEvent e) -> {
            // Navigate to PersonalitiesPage, passing the pitch
            dispose();
            PersonalitiesPage personalitiesPage = new PersonalitiesPage(pitch);
            personalitiesPage.setVisible(true);
        });

        backButton.addActionListener((ActionEvent e) -> {
            // Navigate back to DashboardPage
            dispose();
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.setVisible(true);
        });

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }
}
