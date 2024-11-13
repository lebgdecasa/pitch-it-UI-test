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

public class PersonaPage extends JFrame {

    private Persona persona;
    private Pitch currentPitch;
    public PersonaPage(Persona persona, Pitch pitch) {
        this.persona = persona;
        this.currentPitch = pitch;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Persona");
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

        // Persona Title
        JLabel titleLabel = new JLabel(persona.getName());
        titleLabel.setFont(new Font("Inter", Font.BOLD, 64));
        titleLabel.setBounds(282, 156, 800, 70);
        mainPanel.add(titleLabel);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setBounds(223, 808, 200, 75);
        mainPanel.add(backButton);
        backButton.addActionListener(e -> {
            // Navigate back to PersonasListPage
            dispose();
            PersonasListPage personasListPage = new PersonasListPage(currentPitch); // Pass the currentPitch
            personasListPage.setVisible(true);
        });


        // Chat Button
        Button chatButton = new Button("Chat");
        chatButton.setBounds(759, 808, 200, 75);
        mainPanel.add(chatButton);

        // Ellipse Image (Persona Avatar)
        URL ellipseUrl = getClass().getResource("/ellipse-2.png"); // Assuming PNG format
        ImageIcon ellipseIcon = new ImageIcon(Objects.requireNonNull(ellipseUrl));
        JLabel ellipseLabel = new JLabel(ellipseIcon);
        ellipseLabel.setBounds(140, 286, 231, 220);
        mainPanel.add(ellipseLabel);

        // Fave Food, Age, Hometown, Hobby
        JLabel infoLabel = new JLabel("<html>"
                + "<b>Fave Food: </b>" + persona.getFavoriteFood() + "<br>"
                + "<b>Age: </b>" + persona.getAge() + "<br>"
                + "<b>Hometown: </b>" + persona.getHometown() + "<br>"
                + "<b>Hobby: </b>" + persona.getHobby()
                + "</html>");
        infoLabel.setFont(new Font("Inter", Font.PLAIN, 36));
        infoLabel.setBounds(132, 544, 400, 200);
        mainPanel.add(infoLabel);

        // About Section
        JLabel aboutLabel = new JLabel("<html><b>About<br></b>" + persona.getAbout() + "</html>");
        aboutLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        aboutLabel.setBounds(610, 295, 686, 157);
        mainPanel.add(aboutLabel);

        // Stats Section
        JLabel statsLabel = new JLabel("<html><b>Stats<br></b>" + persona.getStats() + "</html>");
        statsLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        statsLabel.setBounds(610, 467, 686, 157);
        mainPanel.add(statsLabel);

        // Quote Section
        JLabel quoteLabel = new JLabel("<html><b>Quote<br></b><i>“" + persona.getQuote() + "”</i></html>");
        quoteLabel.setFont(new Font("Inter", Font.ITALIC, 24));
        quoteLabel.setBounds(610, 639, 686, 157);
        mainPanel.add(quoteLabel);

        // Action Listeners
        backButton.addActionListener(e -> {
            // Navigate back to PersonasListPage
            dispose();
            PersonasListPage personasListPage = new PersonasListPage(currentPitch);
            personasListPage.setVisible(true);
        });

        chatButton.addActionListener(e -> {
            // Navigate to ChatPage (to be implemented)
            JOptionPane.showMessageDialog(this, "Chat functionality not implemented yet.");
        });

        // Add main panel to the frame
        add(mainPanel);
    }
}
