// ui/components/PersonaPanel.java
package ui.components;

import domain.models.Persona;
import domain.models.Pitch;
import ui.PersonaPage;

import javax.swing.*;
import java.awt.*;

public class PersonaPanel extends JPanel {

    private Persona persona;
    private Pitch currentPitch;
    private boolean showChatButton;

    public PersonaPanel(Persona persona, Pitch pitch) {
        this.persona = persona;
        this.currentPitch = pitch;
        this.showChatButton = showChatButton;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header Panel with Name and Info Button
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(persona.getName());
        nameLabel.setFont(new Font("Inter", Font.BOLD, 32));

        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> {
            // Open PersonaPage as a modal dialog without the "Chat" button
            PersonaPage personaPage = new PersonaPage(persona, currentPitch, false);
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), persona.getName(), true);
            dialog.setContentPane(personaPage);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        headerPanel.add(nameLabel);
        headerPanel.add(infoButton);

        add(headerPanel, BorderLayout.NORTH);

        // Persona Picture
        JLabel imageLabel = new JLabel();
        if (persona.getAvatar() != null) {
            imageLabel.setIcon(persona.getAvatar());
        } else {
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
        }

        add(imageLabel, BorderLayout.CENTER);

        // GPT Output Panel
        JTextArea gptOutputArea = new JTextArea();
        gptOutputArea.setLineWrap(true);
        gptOutputArea.setWrapStyleWord(true);
        gptOutputArea.setEditable(false);
        gptOutputArea.setFont(new Font("Inter", Font.PLAIN, 16));
        gptOutputArea.setText(getGPTResponse());

        JScrollPane scrollPane = new JScrollPane(gptOutputArea);
        scrollPane.setPreferredSize(new Dimension(100, 200)); // Adjust as needed

        add(scrollPane, BorderLayout.SOUTH);
    }

    private String getGPTResponse() {
        // Implement the logic to get GPT response for the persona about the pitch
        // For now, return a placeholder text
        return "GPT API output about the pitch, how " + persona.getName() + " reacted to it.";
    }
}
