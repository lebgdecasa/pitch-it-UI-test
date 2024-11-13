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

        //Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        //Logo
        URL logoUrl = getClass().getResource("/pitch-t-logo.png");
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(logoUrl));
        JLabel logoLabel = new JLabel(logoIcon);

        // Main panel with absolute layout
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        // HamburgerMenu
        HamburgerMenu hamburgerMenu = new HamburgerMenu() {
            @Override
            protected void navigateToDashboard() {
                // Implement navigation if needed
            }

            @Override
            protected void navigateToNewPitch() {
                // Implement navigation if needed
            }

            @Override
            protected void navigateToPersonalities() {
                // Implement navigation if needed
            }

            @Override
            protected void logout() {
                // Implement logout if needed
            }
        };

        // Persona Title
        JLabel titleLabel = new JLabel(persona.getName());
        titleLabel.setFont(new Font("Inter", Font.BOLD, 64));

        // Header components layout
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(logoLabel);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.add(hamburgerMenu);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(menuPanel, BorderLayout.CENTER);
        headerPanel.add(titlePanel, BorderLayout.SOUTH);

        // Add header panel to the main panel
        add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Persona Avatar
        URL avatarUrl = getClass().getResource("/ellipse-2.png"); // Assuming PNG format
        ImageIcon avatarIcon = new ImageIcon(Objects.requireNonNull(avatarUrl));
        JLabel avatarLabel = new JLabel(avatarIcon);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        contentPanel.add(avatarLabel, gbc);

        // Info Labels (Fave Food, Age, Hometown, Hobby)
        JLabel infoLabel = new JLabel("<html>"
                + "<b>Fave Food: </b>" + persona.getFavoriteFood() + "<br>"
                + "<b>Age: </b>" + persona.getAge() + "<br>"
                + "<b>Hometown: </b>" + persona.getHometown() + "<br>"
                + "<b>Hobby: </b>" + persona.getHobby()
                + "</html>");
        infoLabel.setFont(new Font("Inter", Font.PLAIN, 36));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(infoLabel, gbc);

        // About Section
        JLabel aboutLabel = new JLabel("<html><b>About<br></b>" + persona.getAbout() + "</html>");
        aboutLabel.setFont(new Font("Inter", Font.PLAIN, 24));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        contentPanel.add(aboutLabel, gbc);

        // Stats Section
        JLabel statsLabel = new JLabel("<html><b>Stats<br></b>" + persona.getStats() + "</html>");
        statsLabel.setFont(new Font("Inter", Font.PLAIN, 24));

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(statsLabel, gbc);

        // Quote Section
        JLabel quoteLabel = new JLabel("<html><b>Quote<br></b><i>“" + persona.getQuote() + "”</i></html>");
        quoteLabel.setFont(new Font("Inter", Font.ITALIC, 24));

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(quoteLabel, gbc);

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
                // Navigate to ChatPage (to be implemented)
                JOptionPane.showMessageDialog(this, "Chat functionality not implemented yet.");
            });
            footerPanel.add(chatButton);
        }

        // Add footer panel to the main panel
        add(footerPanel, BorderLayout.SOUTH);
    }
}
