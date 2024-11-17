// ui/ComparePersonasPage.java
package ui;

import application.services.PersonaService;
import ui.components.HamburgerMenu;
import ui.components.PersonaPanel;
import domain.models.Persona;
import domain.models.Pitch;
import application.services.ChatService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ComparePersonasPage extends JFrame {

    private Persona persona1;
    private Persona persona2;
    private final Pitch currentPitch;
    private ChatService chatService;

    public ComparePersonasPage(Persona persona1, Persona persona2, Pitch pitch) {
        this.persona1 = persona1;
        this.persona2 = persona2;
        this.currentPitch = pitch;
        this.chatService = new ChatService();
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Compare Personas");
        setSize(1440, 1024);
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

// Logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/pitch-t-logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

// Add logo panel to the header
        headerPanel.add(logoPanel, BorderLayout.WEST);

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
        headerPanel.add(hamburgerMenu, BorderLayout.EAST);

// Title Label (optional)
        JLabel titleLabel = new JLabel("Compare Personas");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // Adds horizontal spacing between panels
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding around the panel

        // Persona Panels
        PersonaPanel personaPanel1 = new PersonaPanel(persona1, currentPitch);
        PersonaPanel personaPanel2 = new PersonaPanel(persona2, currentPitch);

        centerPanel.add(personaPanel1);
        centerPanel.add(personaPanel2);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);

        // Back Button
        Button backButton = new Button("Back");
        backButton.addActionListener(e -> {
            // Navigate back to PersonasListPage
            dispose();
            List<Persona> personas = PersonaService.getInstance().getAllPersonas();
            PersonasListPage personasListPage = new PersonasListPage(personas, currentPitch);
            personasListPage.setVisible(true);
        });

        footerPanel.add(backButton);

        // Add footer panel to main panel
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }
}
