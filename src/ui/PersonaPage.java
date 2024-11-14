// ui/PersonaPage.java
package ui;

import ui.components.Button;
import ui.components.HamburgerMenu;

import domain.models.Persona;
import domain.models.Pitch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.net.URL;
import java.util.Objects;

public class PersonaPage extends JPanel {

    private Persona persona;
    private Pitch currentPitch;
    private boolean showChatButton;

    public PersonaPage(Persona persona, Pitch pitch, boolean showChatButton) {
        this.persona = persona;
        this.currentPitch = pitch;
        this.showChatButton = showChatButton;
        initializeUI();
    }

    private void initializeUI() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Main panel (optional, can be removed if not needed)
        //JPanel mainPanel = new JPanel(new BorderLayout());
        //mainPanel.setBackground(Color.WHITE);

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
        JLabel titleLabel = new JLabel(persona.getName());
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

// HamburgerMenu
        HamburgerMenu hamburgerMenu = new HamburgerMenu() {
            @Override
            protected void navigateToDashboard() {
                // Implement navigation
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PersonaPage.this);
                topFrame.dispose();
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.setVisible(true);
            }

            @Override
            protected void navigateToNewPitch() {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PersonaPage.this);
                topFrame.dispose();
                NewPitchPage newPitchPage = new NewPitchPage();
                newPitchPage.setVisible(true);
            }

            @Override
            protected void navigateToPersonalities() {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PersonaPage.this);
                topFrame.dispose();
                PersonalitiesPage personalitiesPage = new PersonalitiesPage();
                personalitiesPage.setVisible(true);
            }

            @Override
            protected void navigateToAccountSettings() {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PersonaPage.this);
                topFrame.dispose();
                AccountSettingsPage accountSettingsPage = new AccountSettingsPage();
                accountSettingsPage.setVisible(true);
            }
        };

// Add components to the header panel
        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(hamburgerMenu, BorderLayout.EAST);

// Add header panel to the main panel
        add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

// Persona Avatar

        ImageIcon avatarIcon = scaleImageIcon(persona.getAvatar(), 200, 200); // Assuming the persona has an avatar ImageIcon
        JLabel avatarLabel = new JLabel(avatarIcon);
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setVerticalAlignment(JLabel.CENTER);

        contentPanel.add(avatarLabel, gbc);

// Info Labels (Fave Food, Age, Hometown, Hobby)
        JLabel infoLabel = new JLabel("<html>"
                + "<b>Fave Food: </b>" + persona.getFavoriteFood() + "<br>"
                + "<b>Age: </b>" + persona.getAge() + "<br>"
                + "<b>Hometown: </b>" + persona.getHometown() + "<br>"
                + "<b>Hobby: </b>" + persona.getHobby()
                + "</html>");
        infoLabel.setFont(new Font("Inter", Font.PLAIN, 24));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        contentPanel.add(infoLabel, gbc);

// About Section
        JTextArea aboutTextArea = new JTextArea("About\n" + persona.getAbout());
        aboutTextArea.setFont(new Font("Inter", Font.PLAIN, 18));
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setEditable(false);
        JScrollPane aboutScrollPane = new JScrollPane(aboutTextArea);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 0.35;
        contentPanel.add(aboutScrollPane, gbc);

// Stats Section
        JTextArea statsTextArea = new JTextArea("Stats\n" + persona.getStats());
        statsTextArea.setFont(new Font("Inter", Font.PLAIN, 18));
        statsTextArea.setLineWrap(true);
        statsTextArea.setWrapStyleWord(true);
        statsTextArea.setEditable(false);
        JScrollPane statsScrollPane = new JScrollPane(statsTextArea);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 0.35;
        contentPanel.add(statsScrollPane, gbc);

// Add content panel to the center
        add(contentPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);

// Back Button
        Button backButton = new Button("Back");
        backButton.addActionListener(e -> {
            // Handle navigation based on context
            // If in overlay mode, close the dialog
            // If in full page mode, navigate back to PersonasListPage
            Component parent = SwingUtilities.getWindowAncestor(this);
            if (parent instanceof JDialog) {
                ((JDialog) parent).dispose();
            } else {
                // Navigate back to PersonasListPage
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.dispose();
                PersonasListPage personasListPage = new PersonasListPage(currentPitch);
                personasListPage.setVisible(true);
            }
        });
        footerPanel.add(backButton);

// Chat Button (conditionally added)
        if (showChatButton) {
            Button chatButton = new Button("Chat");
            chatButton.addActionListener(e -> {
                // Navigate to PersonaChatPage
                // Dispose of the current window
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.dispose();

                // Create and display the PersonaChatPage
                PersonaChatPage chatPage = new PersonaChatPage(persona, currentPitch);
                chatPage.setVisible(true);
            });
            footerPanel.add(chatButton);
        }

// Add footer panel to the main panel
        add(footerPanel, BorderLayout.SOUTH);

    }
    // Utility method to scale images
    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
