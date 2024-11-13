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
    private final Pitch currentPitch; // To know which project we're viewing personas for

    public PersonasListPage(Pitch pitch) {
        this.currentPitch = pitch;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Personas");
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

        // "Personas" Title
        JLabel titleLabel = new JLabel("Personas");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 64));
        titleLabel.setBounds(282, 156, 400, 70);
        mainPanel.add(titleLabel);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setBounds(84, 807, 200, 75);
        mainPanel.add(backButton);

        // Personas List Panel
        JPanel personasPanel = new JPanel();
        personasPanel.setLayout(new BoxLayout(personasPanel, BoxLayout.Y_AXIS));
        personasPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        personasPanel.setBackground(Color.WHITE);

        // Fetch personas from PersonaService
        PersonaService personaService = PersonaService.getInstance();
        List<Persona> personas = personaService.getAllPersonas();
        personaTabs = new ArrayList<>();

        for (Persona persona : personas) {
            PersonaTab personaTab = new PersonaTab(persona);

            // Add action listener for info button
            personaTab.addInfoButtonListener(e -> {
                // Navigate to Persona Detail Page (to be implemented)
                PersonaPage personaPage = new PersonaPage(persona, currentPitch);
                personaPage.setVisible(true);
            });

            personasPanel.add(personaTab);
            personaTabs.add(personaTab);
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(personasPanel);
        scrollPane.setBounds(85, 274, 1304, 500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);

        // "Compare" Button
        Button compareButton = new Button("Compare");
        compareButton.setBounds(526, 807, 200, 75);
        mainPanel.add(compareButton);

        // "Chat" Button
        Button chatButton = new Button("Chat");
        chatButton.setBounds(968, 807, 200, 75);
        mainPanel.add(chatButton);

        // Action Listeners
        backButton.addActionListener(e -> {
            // Navigate back to ProjectPage
            dispose();
            ProjectPage projectPage = new ProjectPage(currentPitch);
            projectPage.setVisible(true);
        });

        compareButton.addActionListener(e -> {
            List<Persona> selectedPersonas = getSelectedPersonas();
            if (selectedPersonas.size() < 2) {
                JOptionPane.showMessageDialog(this, "Please select at least two personas to compare.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Navigate to Compare Personas Page (to be implemented)
            JOptionPane.showMessageDialog(this, "Compare Personas functionality not implemented yet.");
        });

        chatButton.addActionListener(e -> {
            List<Persona> selectedPersonas = getSelectedPersonas();
            if (selectedPersonas.size() != 1) {
                JOptionPane.showMessageDialog(this, "Please select one persona to chat with.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Navigate to Chat Page with selected persona (to be implemented)
            JOptionPane.showMessageDialog(this, "Chat functionality not implemented yet.");
        });

        // Add main panel to the frame
        add(mainPanel);
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
