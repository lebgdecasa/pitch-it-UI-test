// ui/components/HamburgerMenu.java
package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class HamburgerMenu extends JPanel {

    public HamburgerMenu() {
        initializeUI();
    }

    private void initializeUI() {
        // Set up the Hamburger icon (you can use an image or draw it)
        JLabel menuIcon = new JLabel("\u2630"); // Unicode for hamburger menu icon
        menuIcon.setFont(new Font("Arial", Font.PLAIN, 24));
        menuIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add mouse listener to handle clicks
        menuIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMenu(e.getComponent());
            }
        });

        setLayout(new BorderLayout());
        add(menuIcon, BorderLayout.CENTER);
    }

    private void showMenu(Component invoker) {
        // Create the popup menu
        JPopupMenu menu = new JPopupMenu();

        // Add menu items
        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        dashboardItem.addActionListener(e -> navigateToDashboard());
        menu.add(dashboardItem);

        JMenuItem newPitchItem = new JMenuItem("New Pitch");
        newPitchItem.addActionListener(e -> navigateToNewPitch());
        menu.add(newPitchItem);

        JMenuItem personalitiesItem = new JMenuItem("Personalities");
        personalitiesItem.addActionListener(e -> navigateToPersonalities());
        menu.add(personalitiesItem);

        JMenuItem accountSettingsItem = new JMenuItem("Account Settings");
        accountSettingsItem.addActionListener(e -> navigateToAccountSettings());
        menu.add(accountSettingsItem);

        // Show the popup menu
        menu.show(invoker, 0, invoker.getHeight());
    }

    // Abstract methods to be implemented in the parent component
    protected abstract void navigateToDashboard();

    protected abstract void navigateToNewPitch();

    protected abstract void navigateToPersonalities();

    protected abstract void navigateToAccountSettings();
}
