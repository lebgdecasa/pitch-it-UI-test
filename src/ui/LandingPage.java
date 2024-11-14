// ui/LandingPage.java
package ui;

import application.services.AuthService;
import ui.components.HamburgerMenu;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
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

        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Add header panel to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // "Log in" title
        JLabel loginTitle = new JLabel("Log in");
        loginTitle.setFont(new Font("Inter", Font.PLAIN, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(loginTitle, gbc);

        // "Input Username:" label
        JLabel usernameLabel = new JLabel("Input Username:");
        usernameLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(usernameLabel, gbc);

        // Username input field
        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(new Color(0xE3E3E3));
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(usernameField, gbc);

        // "Input Password:" label
        JLabel passwordLabel = new JLabel("Input Password:");
        passwordLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(passwordLabel, gbc);

        // Password input field
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(new Color(0xE3E3E3));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(passwordField, gbc);

        // Log In button
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Inter", Font.PLAIN, 24));
        loginButton.setBackground(new Color(0xECECEC));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        contentPanel.add(loginButton, gbc);

        // "Don't have an account?" label
        JLabel noAccountLabel = new JLabel("Don’t have an account?");
        noAccountLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        contentPanel.add(noAccountLabel, gbc);

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Inter", Font.PLAIN, 24));
        signUpButton.setBackground(new Color(0xECECEC));
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        signUpButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        contentPanel.add(signUpButton, gbc);

        // Add action listeners
        loginButton.addActionListener((ActionEvent e) -> {
            // Handle login action
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

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

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }
}
