// ui/DashboardPage.java
package ui;

import application.services.PitchService;
import domain.models.Pitch;
import ui.components.ContentTab;
import ui.components.HamburgerMenu;
import ui.components.PitchButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class DashboardPage extends JFrame {

    public DashboardPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Dashboard");
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
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pitch-t-logo.png")));
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        // Hamburger Menu
        HamburgerMenu hamburgerMenu = new HamburgerMenu() {
            @Override
            protected void navigateToDashboard() {
                // Already on DashboardPage
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
        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Title Label
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setBackground(Color.WHITE);

        // PitchButton (New Pitch)
        PitchButton newPitchButton = new PitchButton("New Pitch");
        newPitchButton.setPreferredSize(new Dimension(200, 50));
        newPitchButton.addActionListener((ActionEvent e) -> {
            dispose();
            NewPitchPage newPitchPage = new NewPitchPage();
            newPitchPage.setVisible(true);
        });
        buttonPanel.add(newPitchButton);

        // PitchButton (Ask Personalities)
        PitchButton askPersonalitiesButton = new PitchButton("Ask Personalities");
        askPersonalitiesButton.setPreferredSize(new Dimension(200, 50));
        askPersonalitiesButton.addActionListener(e -> {
            dispose();
            PersonalitiesPage personalitiesPage = new PersonalitiesPage();
            personalitiesPage.setVisible(true);
        });
        buttonPanel.add(askPersonalitiesButton);

        contentPanel.add(buttonPanel, BorderLayout.NORTH);

        // Pitch History Panel
        JPanel pitchHistoryPanel = new JPanel();
        pitchHistoryPanel.setLayout(new BoxLayout(pitchHistoryPanel, BoxLayout.Y_AXIS));
        pitchHistoryPanel.setBackground(Color.WHITE);

        // Fetch pitches from PitchService
        PitchService pitchService = PitchService.getInstance();
        java.util.List<Pitch> pitches = pitchService.getAllPitches();

        for (Pitch pitch : pitches) {
            ContentTab tab = new ContentTab(pitch.getName());
            tab.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                    ProjectPage projectPage = new ProjectPage(pitch);
                    projectPage.setVisible(true);
                }
            });
            pitchHistoryPanel.add(tab);
        }

        // Scroll Pane for Pitch History
        JScrollPane scrollPane = new JScrollPane(pitchHistoryPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }
}
