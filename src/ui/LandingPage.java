// ui/LandingPage.java
package ui;

import application.services.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class LandingPage extends JFrame {

    public LandingPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Landing Page");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with absolute layout
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        // Load logo image
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pitch-t-logo.png")));
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(93, 17, 383, 135); // x, y, width, height
        mainPanel.add(logoLabel);

        // "Log in" title
        JLabel loginTitle = new JLabel("Log in");
        loginTitle.setFont(new Font("Inter", Font.PLAIN, 100));
        loginTitle.setBounds(568, 152, 300, 100);
        mainPanel.add(loginTitle);

        // "Input Username:" label
        JLabel usernameLabel = new JLabel("Input Username:");
        usernameLabel.setFont(new Font("Inter", Font.PLAIN, 64));
        usernameLabel.setBounds(471, 275, 500, 70);
        mainPanel.add(usernameLabel);

        // Username input field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(216, 363, 1000, 62);
        usernameField.setBackground(new Color(0xE3E3E3));
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(usernameField);

        // "Input Password:" label
        JLabel passwordLabel = new JLabel("Input Password:");
        passwordLabel.setFont(new Font("Inter", Font.PLAIN, 64));
        passwordLabel.setBounds(477, 437, 500, 70);
        mainPanel.add(passwordLabel);

        // Password input field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(215, 526, 1000, 62);
        passwordField.setBackground(new Color(0xE3E3E3));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(passwordField);

        // Log In button
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Inter", Font.PLAIN, 48));
        loginButton.setBounds(502, 721, 420, 75);
        loginButton.setBackground(new Color(0xECECEC));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loginButton.setFocusPainted(false);
        mainPanel.add(loginButton);

        // "Don't have an account?" label
        JLabel noAccountLabel = new JLabel("Don’t have an account?");
        noAccountLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        noAccountLabel.setBounds(587, 828, 300, 30);
        mainPanel.add(noAccountLabel);

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Inter", Font.PLAIN, 48));
        signUpButton.setBounds(502, 864, 420, 75);
        signUpButton.setBackground(new Color(0xECECEC));
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        signUpButton.setFocusPainted(false);
        mainPanel.add(signUpButton);

        // Add action listeners
        loginButton.addActionListener((ActionEvent e) -> {
            // Handle login action
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // TODO: Implement authentication logic using AuthService
            AuthService authService = new AuthService();
            boolean authenticated = authService.authenticateUser(username, password);

            if (authenticated) {
                // Navigate to Dashboard
                dispose();
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        });

        signUpButton.addActionListener((ActionEvent e) -> {
            // Navigate to Sign Up page

            dispose();
            SignUpPage signUpPage = new SignUpPage();
            signUpPage.setVisible(true);
        });

        // Add main panel to the frame
        add(mainPanel);
    }
}
