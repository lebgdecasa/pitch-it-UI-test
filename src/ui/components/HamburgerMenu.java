// ui/components/HamburgerMenu.java
package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HamburgerMenu extends JPanel {
    private JButton menuButton;
    private JPopupMenu menuPopup;

    public HamburgerMenu() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);

        // Hamburger Button
        menuButton = new JButton("\u2630"); // Unicode for hamburger menu
        menuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        menuButton.setFocusPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setBounds(0, 0, 50, 50);

        add(menuButton);

        // Menu Popup
        menuPopup = new JPopupMenu();

        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        JMenuItem newPitchItem = new JMenuItem("Create a new pitch");
        JMenuItem personalitiesItem = new JMenuItem("Personalities");
        JMenuItem logoutItem = new JMenuItem("Log Out");

        menuPopup.add(dashboardItem);
        menuPopup.add(newPitchItem);
        menuPopup.add(personalitiesItem);
        menuPopup.add(logoutItem);

        // Action Listeners for Menu Items
        dashboardItem.addActionListener(e -> navigateToDashboard());
        newPitchItem.addActionListener(e -> navigateToNewPitch());
        personalitiesItem.addActionListener(e -> navigateToPersonalities());
        logoutItem.addActionListener(e -> logout());

        // Add action listener to show popup menu
        menuButton.addActionListener(e -> menuPopup.show(menuButton, 0, menuButton.getHeight()));
    }

    // Navigation methods to be overridden
    protected void navigateToDashboard() {}
    protected void navigateToNewPitch() {}
    protected void navigateToPersonalities() {}
    protected void logout() {}

    // Method to set up navigation actions
    public void setNavigationActions(ActionListener dashboardAction, ActionListener newPitchAction,
                                     ActionListener personalitiesAction, ActionListener logoutAction) {
        ((JMenuItem) menuPopup.getComponent(0)).addActionListener(dashboardAction);
        ((JMenuItem) menuPopup.getComponent(1)).addActionListener(newPitchAction);
        ((JMenuItem) menuPopup.getComponent(2)).addActionListener(personalitiesAction);
        ((JMenuItem) menuPopup.getComponent(3)).addActionListener(logoutAction);
    }
}
