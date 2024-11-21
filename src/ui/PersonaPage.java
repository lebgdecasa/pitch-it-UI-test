// ui/PersonaPage.java
package ui;

import application.services.PersonaService;
import domain.models.Persona;
import domain.models.Pitch;
import ui.components.Button;
import ui.components.HamburgerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.List;

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

        // Header Panel with Hamburger Menu and Title
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Content Panel with Persona Details
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);

        // Footer Panel with Back Button and Chat Button
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the header panel containing the Hamburger Menu and Persona Name.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Hamburger Menu (Left)
        HamburgerMenu hamburgerMenu = new HamburgerMenu() {
            @Override
            protected void navigateToDashboard() {
                navigateToPage(new DashboardPage());
            }

            @Override
            protected void navigateToNewPitch() {
                navigateToPage(new NewPitchPage());
            }

            @Override
            protected void navigateToPersonalities() {
                navigateToPage(new PersonalitiesPage(currentPitch));
            }

            @Override
            protected void navigateToAccountSettings() {
                navigateToPage(new AccountSettingsPage());
            }

            /**
             * Helper method to navigate to a new page.
             */
            private void navigateToPage(JFrame page) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PersonaPage.this);
                if (topFrame != null) {
                    topFrame.dispose();
                }
                page.setVisible(true);
            }
        };
        headerPanel.add(hamburgerMenu, BorderLayout.WEST);

        // Title Label (Center)
        JLabel titleLabel = new JLabel(persona.getName());
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    /**
     * Creates the main content panel displaying persona details.
     */
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout(20, 20)); // Horizontal and vertical gaps

        // Top Section: Avatar and Basic Info
        JPanel topSection = new JPanel(new BorderLayout(20, 20));
        topSection.setBackground(Color.WHITE);
        topSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Avatar (Left)
        JLabel avatarLabel = new JLabel();
        ImageIcon avatarIcon = scaleImageIcon(persona.getAvatar(), 200, 200);
        if (avatarIcon != null) {
            avatarLabel.setIcon(avatarIcon);
        } else {
            // If avatar not found, display placeholder
            avatarLabel.setText("No Avatar");
            avatarLabel.setHorizontalAlignment(JLabel.CENTER);
            avatarLabel.setVerticalAlignment(JLabel.CENTER);
            avatarLabel.setPreferredSize(new Dimension(200, 200));
            avatarLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        topSection.add(avatarLabel, BorderLayout.WEST);

        // Basic Info (Right)
        JPanel basicInfoPanel = new JPanel();
        basicInfoPanel.setBackground(Color.WHITE);
        basicInfoPanel.setLayout(new BoxLayout(basicInfoPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(persona.getName());
        nameLabel.setFont(new Font("Inter", Font.BOLD, 24));

        JLabel ageLabel = new JLabel("Age: " + persona.getAge());
        ageLabel.setFont(new Font("Inter", Font.PLAIN, 18));

        JLabel educationlabel = new JLabel("Education: " + persona.getEducation());
        educationlabel.setFont(new Font("Inter", Font.PLAIN, 18));

        JLabel occupationlabel = new JLabel("Occupation: " + persona.getOccupation());
        occupationlabel.setFont(new Font("Inter", Font.PLAIN, 18));

        JLabel salarylabel = new JLabel("Salary range: " + persona.getSalaryrange());
        salarylabel.setFont(new Font("Inter", Font.PLAIN, 18));

        JLabel interestsLabel = new JLabel("Interests: " + persona.getInterests());
        interestsLabel.setFont(new Font("Inter", Font.PLAIN, 18));

        basicInfoPanel.add(nameLabel);
        basicInfoPanel.add(Box.createVerticalStrut(10));
        basicInfoPanel.add(ageLabel);
        basicInfoPanel.add(Box.createVerticalStrut(5));
        basicInfoPanel.add(educationlabel);
        basicInfoPanel.add(Box.createVerticalStrut(5));
        basicInfoPanel.add(occupationlabel);
        basicInfoPanel.add(Box.createVerticalStrut(5));
        basicInfoPanel.add(salarylabel);
        basicInfoPanel.add(Box.createVerticalStrut(5));
        basicInfoPanel.add(interestsLabel);

        topSection.add(basicInfoPanel, BorderLayout.CENTER);

        contentPanel.add(topSection, BorderLayout.NORTH);

        // Middle Section: About
        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.setBackground(Color.WHITE);
        aboutPanel.setBorder(BorderFactory.createTitledBorder("About"));

        JTextArea aboutTextArea = new JTextArea(persona.getAbout());
        aboutTextArea.setFont(new Font("Inter", Font.PLAIN, 16));
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setEditable(false);
        aboutTextArea.setBackground(new Color(245, 245, 245)); // Light gray background

        JScrollPane aboutScrollPane = new JScrollPane(aboutTextArea);
        aboutScrollPane.setBorder(null); // Remove border for aesthetics

        aboutPanel.add(aboutScrollPane, BorderLayout.CENTER);

        contentPanel.add(aboutPanel, BorderLayout.CENTER);

        // Bottom Section: Stats
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));

        JTextArea statsTextArea = new JTextArea(persona.getStats());
        statsTextArea.setFont(new Font("Inter", Font.PLAIN, 16));
        statsTextArea.setLineWrap(true);
        statsTextArea.setWrapStyleWord(true);
        statsTextArea.setEditable(false);
        statsTextArea.setBackground(new Color(245, 245, 245)); // Light gray background

        JScrollPane statsScrollPane = new JScrollPane(statsTextArea);
        statsScrollPane.setBorder(null); // Remove border for aesthetics

        statsPanel.add(statsScrollPane, BorderLayout.CENTER);

        contentPanel.add(statsPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    /**
     * Creates the footer panel containing the Back Button and optionally the Chat Button.
     */
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(Color.WHITE);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Inter", Font.PLAIN, 18));
        backButton.addActionListener(e -> {
            // Navigate back to PersonasListPage
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.dispose();
            }
            List<Persona> personas = PersonaService.getInstance().getPersonasForPitch(currentPitch);
            PersonasListPage personasListPage = new PersonasListPage(personas, currentPitch);
            personasListPage.setVisible(true);
        });
        footerPanel.add(backButton);

        // Vision Button
        Button visionButton = new Button("Vision");
        visionButton.setFont(new Font("Inter", Font.PLAIN, 18));
        visionButton.addActionListener(e -> {
            // Navigate to VisionPage
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.dispose();
            }
            VisionPage visionPage = new VisionPage(persona, currentPitch, true); // Pass current pitch if needed
            visionPage.setVisible(true);
        });
        footerPanel.add(visionButton);

        // Chat Button (conditionally added)
        if (showChatButton) {
            Button chatButton = new Button("Chat");
            chatButton.setFont(new Font("Inter", Font.PLAIN, 18));
            chatButton.addActionListener(e -> {
                // Navigate to PersonaChatPage
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame != null) {
                    topFrame.dispose();
                }
                PersonaChatPage chatPage = new PersonaChatPage(persona, currentPitch);
                chatPage.setVisible(true);
            });
            footerPanel.add(chatButton);
        }

        return footerPanel;
    }

    /**
     * Helper method to load an ImageIcon from resources.
     */
    private ImageIcon loadImageIcon(String path) {
        URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Utility method to scale images.
     */
    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
