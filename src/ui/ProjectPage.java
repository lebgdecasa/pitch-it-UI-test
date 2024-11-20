// Updated ProjectPage.java
package ui;

import application.services.AudienceAnalyzer;
import application.services.PersonaService;
import domain.models.Persona;
import ui.components.Button;
import ui.components.HamburgerMenu;
import domain.models.Pitch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProjectPage extends JFrame {

    private final Pitch pitch;

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
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Logo Panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);

        // Logo
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

        // Add components to header panel
        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(hamburgerMenu, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.LIGHT_GRAY);
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

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

        // Target Audience Panel with Info Buttons
        JPanel targetAudiencePanel = new JPanel();
        targetAudiencePanel.setLayout(new BoxLayout(targetAudiencePanel, BoxLayout.Y_AXIS));
        targetAudiencePanel.setBackground(Color.WHITE);
        targetAudiencePanel.setBorder(BorderFactory.createTitledBorder("Target Audience"));

        String[] targetAudience = pitch.getTargetAudience().split(";");
        for (String audience : targetAudience) {
            JPanel audienceRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            audienceRow.setBackground(Color.WHITE);

            JLabel audienceLabel = new JLabel(audience.trim());
            audienceLabel.setFont(new Font("Inter", Font.PLAIN, 18));

            JButton infoButton = new JButton("Info");
            infoButton.setFont(new Font("Inter", Font.PLAIN, 14));
            infoButton.addActionListener(new InfoButtonListener(audience));

            audienceRow.add(audienceLabel);
            audienceRow.add(infoButton);
            targetAudiencePanel.add(audienceRow);
        }

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        contentPanel.add(new JScrollPane(targetAudiencePanel), gbc);

        return contentPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(Color.WHITE);

        // Edit Button
        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Inter", Font.PLAIN, 24));
        ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/edit.png")));
        editButton.setIcon(editIcon);
        footerPanel.add(editButton);

        editButton.addActionListener((ActionEvent e) -> {
            // Implement edit functionality
            JOptionPane.showMessageDialog(this, "Edit functionality not implemented yet.");
        });

        // View Personas Button
        Button viewPersonasButton = new Button("View Personas");
        footerPanel.add(viewPersonasButton);

        viewPersonasButton.addActionListener((ActionEvent e) -> {
            // Check if personas are already associated with the pitch
            List<Persona> personas = pitch.getPersonas();

            if (personas == null || personas.isEmpty()) {
                // If no personas exist, generate them
                String targetAudience = pitch.getTargetAudience();
                String projectDescription = pitch.getDescription();

                try {
                    personas = PersonaService.getInstance().generatePersonas(targetAudience, projectDescription, pitch);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error generating personas.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            // Navigate to PersonasListPage with the personas
            dispose();
            PersonasListPage personasListPage = new PersonasListPage(personas, pitch);
            personasListPage.setVisible(true);
        });

        // Ask Personalities Button
        Button askPersonalitiesButton = new Button("Ask Personalities");
        footerPanel.add(askPersonalitiesButton);

        askPersonalitiesButton.addActionListener((ActionEvent e) -> {
            // Navigate to PersonalitiesPage, passing the pitch
            dispose();
            PersonalitiesPage personalitiesPage = new PersonalitiesPage(pitch);
            personalitiesPage.setVisible(true);
        });

        // Back Button
        Button backButton = new Button("Back");
        footerPanel.add(backButton);

        backButton.addActionListener((ActionEvent e) -> {
            // Navigate back to DashboardPage
            dispose();
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.setVisible(true);
        });

        return footerPanel;
    }

    private class InfoButtonListener implements ActionListener {
        private final String audience;

        public InfoButtonListener(String audience) {
            this.audience = audience;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingWorker<String, Void> worker = new SwingWorker<>() {
                @Override
                protected String doInBackground() throws Exception {
                    return AudienceAnalyzer.detailedTA(audience);
                }

                @Override
                protected void done() {
                    try {
                        String details = get();
                        JOptionPane.showMessageDialog(ProjectPage.this, details, "Target Audience Details", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ProjectPage.this, "Error fetching details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }
}
