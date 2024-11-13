// ui/components/PersonaTab.java
package ui.components;

import domain.models.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class PersonaTab extends JPanel {
    private JCheckBox checkBox;
    private JLabel nameLabel;
    private JButton infoButton;
    private Persona persona;

    public PersonaTab(Persona persona) {
        this.persona = persona;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);

        // Left side: Checkbox
        checkBox = new JCheckBox();
        add(checkBox, BorderLayout.WEST);

        // Center: Persona name
        nameLabel = new JLabel(persona.getName());
        nameLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        add(nameLabel, BorderLayout.CENTER);

        // Right side: Info button
        infoButton = new JButton();
        URL infoIconUrl = getClass().getResource("/info.png");
        if (infoIconUrl != null) {
            ImageIcon infoIcon = new ImageIcon(infoIconUrl);
            infoButton.setIcon(infoIcon);
        } else {
            infoButton.setText("Info");
        }
        infoButton.setPreferredSize(new Dimension(50, 50));
        infoButton.setFocusPainted(false);
        add(infoButton, BorderLayout.EAST);

        // Set preferred size for consistent layout
        setPreferredSize(new Dimension(1000, 60));
    }

    // Getter methods
    public boolean isSelected() {
        return checkBox.isSelected();
    }

    public Persona getPersona() {
        return persona;
    }

    public void addInfoButtonListener(ActionListener listener) {
        infoButton.addActionListener(listener);
    }
}
