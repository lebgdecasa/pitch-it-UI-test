// ui/components/PersonalityBox.java
package ui.components;

import domain.models.Personality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PersonalityBox extends JPanel {
    private Personality personality;
    private JButton infoButton;
    private JLabel nameLabel;
    private JLabel avatarLabel;

    public PersonalityBox(Personality personality) {
        this.personality = personality;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(286, 100));
        setBackground(Color.WHITE);

        // Avatar
        avatarLabel = new JLabel(personality.getAvatar());
        avatarLabel.setPreferredSize(new Dimension(64, 64));
        add(avatarLabel, BorderLayout.WEST);

        // Name
        nameLabel = new JLabel(personality.getName());
        nameLabel.setFont(new Font("Inter", Font.PLAIN, 18));
        add(nameLabel, BorderLayout.CENTER);

        // Info Button
        infoButton = new JButton("Info");
        infoButton.setPreferredSize(new Dimension(50, 50));
        add(infoButton, BorderLayout.EAST);
    }

    public void addInfoButtonListener(ActionListener listener) {
        infoButton.addActionListener(listener);
    }

    public Personality getPersonality() {
        return personality;
    }
}
