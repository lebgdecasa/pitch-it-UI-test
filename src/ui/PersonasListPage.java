// ui/PersonasListPage.java
package ui;

import domain.models.Pitch;
import ui.components.Button;
import ui.components.HamburgerMenu;
import ui.components.PersonaTab;

import application.services.PersonaService;
import domain.models.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonasListPage extends JFrame {

    private List<PersonaTab> personaTabs;
    private final Pitch currentPitch;
    private final List<Persona> personas;// To know which project we're viewing personas for

    public PersonasListPage(List<Persona> personas, Pitch pitch) {
        this.currentPitch = pitch;
        this.personas = personas;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Personas");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        // Load logo image
        URL logoUrl = getClass().getResource("/pitch-t-logo.png");
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(logoUrl));
        JLabel logoLabel = new JLabel(logoIcon);

        // "Personas" Title
        JLabel titleLabel = new JLabel("Personas");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 64));

        // HamburgerMenu
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
        headerPanel.add(hamburgerMenu, BorderLayout.WEST);

        // Add components to headerPanel
        headerPanel.add(logoLabel);

        headerPanel.add(titleLabel);

        // Add header panel to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Personas List Panel
        JPanel personasPanel = new JPanel();
        personasPanel.setLayout(new BoxLayout(personasPanel, BoxLayout.Y_AXIS));
        personasPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        personasPanel.setBackground(Color.WHITE);


// Fetch personas from the provided list
        personaTabs = new ArrayList<>();

        for (Persona persona : personas) {
            PersonaTab personaTab = new PersonaTab(persona);

            // Add action listener for info button
            personaTab.addInfoButtonListener(e -> {
                // Navigate to Persona Detail Page
                dispose();

                // Create a new JFrame to hold the PersonaPage panel
                JFrame frame = new JFrame("Persona Details");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationRelativeTo(null);

                // Create the PersonaPage panel
                PersonaPage personaPage = new PersonaPage(persona, currentPitch, true);

                // Add the panel to the frame
                frame.setContentPane(personaPage);

                // Display the frame
                frame.setVisible(true);
            });

            personasPanel.add(personaTab);
            personaTabs.add(personaTab);
        }


        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(personasPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Center Panel for Content
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Add center panel to main panel
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);

        // "Back" Button
        Button backButton = new Button("Back");

        // "Compare" Button
        Button compareButton = new Button("Compare");

        // "Vision" Button
        Button visionButton = new Button("Vision");

        // "Chat" Button
        Button chatButton = new Button("Chat");

        // Add buttons to footer panel
        footerPanel.add(backButton);
        footerPanel.add(compareButton);
        footerPanel.add(chatButton);
        footerPanel.add(visionButton);

        // Add footer panel to main panel
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Set the main panel as the content pane
        setContentPane(mainPanel);

        // Action Listeners
        backButton.addActionListener(e -> {
            // Navigate back to ProjectPage
            dispose();
            ProjectPage projectPage = new ProjectPage(currentPitch);
            projectPage.setVisible(true);
        });

        visionButton.addActionListener(e -> {
            // Navigate to VisionPage
            if (getSelectedPersonas().size()!=1){
                JOptionPane.showMessageDialog(null, "Please select one persona to view Vision", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Persona selectedPersona = getSelectedPersonas().get(0);
            dispose();
            VisionPage visionPage = new VisionPage(selectedPersona, currentPitch, false); // Pass current pitch if needed
            visionPage.setVisible(true);
        });

        compareButton.addActionListener(e -> {
            List<Persona> selectedPersonas = getSelectedPersonas();
            if (selectedPersonas.size() < 2) {
                JOptionPane.showMessageDialog(this, "Please select at least two personas to compare.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedPersonas.size() > 2) {
                JOptionPane.showMessageDialog(this, "Please select only two personas to compare.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Navigate to ComparePersonasPage
            dispose();
            ComparePersonasPage comparePage = new ComparePersonasPage(selectedPersonas.get(0), selectedPersonas.get(1), currentPitch);
            comparePage.setVisible(true);
        });

        chatButton.addActionListener(e -> {
            List<Persona> selectedPersonas = getSelectedPersonas();
            if (selectedPersonas.size() != 1) {
                JOptionPane.showMessageDialog(this, "Please select one persona to chat with.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Navigate to PersonaChatPage with selected persona
            dispose();

            // Create and display the PersonaChatPage
            PersonaChatPage chatPage = new PersonaChatPage(selectedPersonas.get(0), currentPitch);
            chatPage.setVisible(true);
        });
    }

    private List<Persona> getSelectedPersonas() {
        List<Persona> selectedPersonas = new ArrayList<>();
        for (PersonaTab tab : personaTabs) {
            if (tab.isSelected()) {
                selectedPersonas.add(tab.getPersona());
            }
        }
        return selectedPersonas;
    }
}
