// ui/SignUpPage.java
package ui;

import application.services.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Objects;

public class SignUpPage extends JFrame {

    public SignUpPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Sign Up");
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
        logoLabel.setBounds(93, 17, 383, 135); // x, y, width, height
        mainPanel.add(logoLabel);

        // "Sign Up" title
        JLabel signUpTitle = new JLabel("Sign Up");
        signUpTitle.setFont(new Font("Inter", Font.PLAIN, 70));
        signUpTitle.setBounds(619, 151, 300, 100);
        mainPanel.add(signUpTitle);

        // "Choose Username:" label
        JLabel usernameLabel = new JLabel("Choose Username:");
        usernameLabel.setFont(new Font("Inter", Font.PLAIN, 32));
        usernameLabel.setBounds(430, 271, 500, 70);
        mainPanel.add(usernameLabel);

        // Username input field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(228, 366, 1000, 62);
        usernameField.setBackground(new Color(0xE3E3E3));
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(usernameField);

        // "Choose Password:" label
        JLabel passwordLabel = new JLabel("Choose Password:");
        passwordLabel.setFont(new Font("Inter", Font.PLAIN, 32));
        passwordLabel.setBounds(430, 452, 500, 70);
        mainPanel.add(passwordLabel);

        // Password input field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(233, 542, 1000, 62);
        passwordField.setBackground(new Color(0xE3E3E3));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(passwordField);

        // "Repeat Password:" label
        JLabel repeatPasswordLabel = new JLabel("Repeat Password:");
        repeatPasswordLabel.setFont(new Font("Inter", Font.PLAIN, 32));
        repeatPasswordLabel.setBounds(439, 631, 500, 70);
        mainPanel.add(repeatPasswordLabel);

        // Repeat Password input field
        JPasswordField repeatPasswordField = new JPasswordField();
        repeatPasswordField.setBounds(233, 752, 1000, 62);
        repeatPasswordField.setBackground(new Color(0xE3E3E3));
        repeatPasswordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(repeatPasswordField);

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Inter", Font.PLAIN, 32));
        signUpButton.setBounds(233, 857, 420, 75);
        signUpButton.setBackground(new Color(0xECECEC));
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        signUpButton.setFocusPainted(false);
        mainPanel.add(signUpButton);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Inter", Font.PLAIN, 32));
        cancelButton.setBounds(813, 857, 420, 75);
        cancelButton.setBackground(new Color(0xECECEC));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setFocusPainted(false);
        mainPanel.add(cancelButton);

        // Add action listeners
        signUpButton.addActionListener((ActionEvent e) -> {
            // Handle sign-up action
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String repeatPassword = new String(repeatPasswordField.getPassword());

            // Validate inputs
            if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(repeatPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Use AuthService to register the user
            AuthService authService = new AuthService();
            boolean success = authService.registerUser(username, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Navigate to Dashboard
                dispose();
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            // Navigate back to Landing Page
            dispose();
            LandingPage landingPage = new LandingPage();
            landingPage.setVisible(true);
        });

        // Add main panel to the frame
        add(mainPanel);
    }
}
