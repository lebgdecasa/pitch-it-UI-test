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
        HamburgerMenu hamburgerMenu = new HamburgerMenu();

        // Add components to headerPanel
        headerPanel.add(logoLabel);
        headerPanel.add(hamburgerMenu);
        headerPanel.add(titleLabel);

        // Add header panel to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

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
                // Navigate to Persona Detail Page
                dispose();
                PersonaPage personaPage = new PersonaPage(persona, currentPitch, true);
                personaPage.setVisible(true);
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

        // "Chat" Button
        Button chatButton = new Button("Chat");

        // Add buttons to footer panel
        footerPanel.add(backButton);
        footerPanel.add(compareButton);
        footerPanel.add(chatButton);

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
            // Navigate to Chat Page with selected persona (to be implemented)
            JOptionPane.showMessageDialog(this, "Chat functionality not implemented yet.");
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
