// ui/SignUpPage.java
package ui;

import application.services.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SignUpPage extends JFrame {

    public SignUpPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Sign Up");
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
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/pitch-t-logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        // Add logo panel to header
        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Add header panel to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // "Sign Up" title
        JLabel signUpTitle = new JLabel("Sign Up");
        signUpTitle.setFont(new Font("Inter", Font.PLAIN, 48));
        signUpTitle.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(signUpTitle, gbc);

        // "Choose Username:" label
        JLabel usernameLabel = new JLabel("Choose Username:");
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

        // "Choose Password:" label
        JLabel passwordLabel = new JLabel("Choose Password:");
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

        // "Repeat Password:" label
        JLabel repeatPasswordLabel = new JLabel("Repeat Password:");
        repeatPasswordLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(repeatPasswordLabel, gbc);

        // Repeat Password input field
        JPasswordField repeatPasswordField = new JPasswordField(20);
        repeatPasswordField.setBackground(new Color(0xE3E3E3));
        repeatPasswordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPanel.add(repeatPasswordField, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Inter", Font.PLAIN, 24));
        signUpButton.setBackground(new Color(0xECECEC));
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        signUpButton.setFocusPainted(false);
        buttonPanel.add(signUpButton);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Inter", Font.PLAIN, 24));
        cancelButton.setBackground(new Color(0xECECEC));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setFocusPainted(false);
        buttonPanel.add(cancelButton);

        // Add button panel to content panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        contentPanel.add(buttonPanel, gbc);

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

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }
}