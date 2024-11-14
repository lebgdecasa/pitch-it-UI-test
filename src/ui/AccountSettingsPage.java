// ui/AccountSettingsPage.java
package ui;

import ui.components.Button;
import ui.components.HamburgerMenu;
import application.services.AuthService;

import javax.swing.*;
import java.awt.*;

public class AccountSettingsPage extends JFrame {

    public AccountSettingsPage() {
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Account Settings");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout for responsiveness
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header with logo and hamburger menu
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Load logo image
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/pitch-t-logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Hamburger Menu
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
                // Already on AccountSettingsPage
            }
        };
        headerPanel.add(hamburgerMenu, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Panel for Account Options
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // "Change Password" Button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Inter", Font.PLAIN, 24));
        changePasswordButton.setBackground(new Color(0xECECEC));
        changePasswordButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        changePasswordButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(changePasswordButton, gbc);

        // "Log Out" Button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Inter", Font.PLAIN, 24));
        logoutButton.setBackground(new Color(0xECECEC));
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        logoutButton.setFocusPainted(false);
        gbc.gridy = 1;
        centerPanel.add(logoutButton, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        changePasswordButton.addActionListener(e -> openChangePasswordDialog());

        logoutButton.addActionListener(e -> {
            // Perform logout action
            AuthService authService = AuthService.getInstance();
            authService.logout();

            // Navigate back to the landing page
            dispose();
            LandingPage landingPage = new LandingPage();
            landingPage.setVisible(true);
        });

        // Add main panel to the frame
        add(mainPanel);
    }

    private void openChangePasswordDialog() {
        JDialog dialog = new JDialog(this, "Change Password", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Old Password Label and Field
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JPasswordField oldPasswordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(oldPasswordLabel, gbc);

        gbc.gridx = 1;
        dialog.add(oldPasswordField, gbc);

        // New Password Label and Field
        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        dialog.add(newPasswordField, gbc);

        // Confirm Password Label and Field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        dialog.add(confirmPasswordField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        // Action Listeners
        submitButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(dialog, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AuthService authService = AuthService.getInstance();
            boolean success = authService.changePassword(oldPassword, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
