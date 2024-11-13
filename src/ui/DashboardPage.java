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
import java.net.URL;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class DashboardPage extends JFrame {

    public DashboardPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Dashboard");
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
            protected void logout() {
                dispose();
                LandingPage landingPage = new LandingPage();
                landingPage.setVisible(true);
            }
        };
        hamburgerMenu.setBounds(10, 10, 50, 50);
        mainPanel.add(hamburgerMenu);

        // PitchButton (New Pitch)
        PitchButton newPitchButton = new PitchButton("New Pitch");
        newPitchButton.setBounds(296, 153, 150, 50);
        mainPanel.add(newPitchButton);
        newPitchButton.addActionListener((ActionEvent e) -> {
            dispose();
            NewPitchPage newPitchPage = new NewPitchPage();
            newPitchPage.setVisible(true);
        });


        // PitchButton (Ask Personalities)
        PitchButton askPersonalitiesButton = new PitchButton("Ask Personalities");
        askPersonalitiesButton.setBounds(613, 152, 727, 50);
        mainPanel.add(askPersonalitiesButton);
        askPersonalitiesButton.addActionListener(e -> {
            dispose();
            PersonalitiesPage personalitiesPage = new PersonalitiesPage();
            personalitiesPage.setVisible(true);
        });

        // Pitch History Panel
        JPanel pitchHistoryPanel = new JPanel();
        pitchHistoryPanel.setLayout(new BoxLayout(pitchHistoryPanel, BoxLayout.Y_AXIS));
        pitchHistoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pitchHistoryPanel.setBackground(Color.WHITE);
        pitchHistoryPanel.setBounds(70, 281, 1270, 708);
        // After setting up pitchHistoryPanel
        PitchService pitchService = PitchService.getInstance();
        List<Pitch> pitches = pitchService.getAllPitches();

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
            // Optionally add action listeners to each tab
            pitchHistoryPanel.add(tab);
        }


        // ContentTabs
        ContentTab tab1 = new ContentTab("Facebook for Cats");
        ContentTab tab2 = new ContentTab("Time-traveling socks");
        ContentTab tab3 = new ContentTab("Potato that peels itself");
        ContentTab tab4 = new ContentTab("App to pitch projects to AI Personas");

        pitchHistoryPanel.add(tab1);
        pitchHistoryPanel.add(tab2);
        pitchHistoryPanel.add(tab3);
        pitchHistoryPanel.add(tab4);

        mainPanel.add(pitchHistoryPanel);

        // Scroll Bar (if needed)
        JScrollPane scrollPane = new JScrollPane(pitchHistoryPanel);
        scrollPane.setBounds(70, 281, 1270, 708);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);

        // Add main panel to the frame
        add(mainPanel);
    }
}
