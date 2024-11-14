// ui/components/PersonalityBox.java
package ui.components;

import domain.models.Personality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PersonalityBox extends JPanel {

    private Personality personality;
    private JButton infoButton;

    public PersonalityBox(Personality personality) {
        this.personality = personality;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // Set maximum height

        // Left: Avatar Image
        JLabel avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(64, 64));
        avatarLabel.setIcon(scaleImageIcon(personality.getAvatar(), 64, 64));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setVerticalAlignment(JLabel.CENTER);

        add(avatarLabel, BorderLayout.WEST);

        // Center: Personality Name
        JLabel nameLabel = new JLabel(personality.getName());
        nameLabel.setFont(new Font("Inter", Font.BOLD, 24));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(nameLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Right: Info Button
        infoButton = new JButton("Info");
        add(infoButton, BorderLayout.EAST);
    }

    public void addInfoButtonListener(ActionListener listener) {
        infoButton.addActionListener(listener);
    }

    // Utility method to scale images
    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
