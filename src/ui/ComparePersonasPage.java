package ui;

import ui.components.Button;
import ui.components.HamburgerMenu;
import java.util.List;
import domain.models.Persona;
import domain.models.Pitch;

import javax.swing.*;
import java.awt.*;

public class ComparePersonasPage extends JFrame {

    private Persona persona1;
    private Persona persona2;
    private final Pitch currentPitch;

    public ComparePersonasPage(Persona persona1, Persona persona2, Pitch pitch) {
        this.persona1 = persona1;
        this.persona2 = persona2;
        this.currentPitch = pitch;
        initializeUI();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Pitch!t - Compare Personas");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Set main panel as content pane
        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(1440, 100)); // Reduced height to 100px

        // Logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/pitch-t-logo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(10, 10, 120, 80); // Scaled to fit
        headerPanel.add(logoLabel);

        // Hamburger Menu
        HamburgerMenu hamburgerMenu = new HamburgerMenu() {
            @Override
            protected void navigateToDashboard() {
                dispose();
                new DashboardPage().setVisible(true);
            }

            @Override
            protected void navigateToNewPitch() {
                dispose();
                new NewPitchPage().setVisible(true);
            }

            @Override
            protected void navigateToPersonalities() {
                dispose();
                new PersonalitiesPage(currentPitch).setVisible(true);
            }

            @Override
            protected void navigateToAccountSettings() {
                dispose();
                new AccountSettingsPage().setVisible(true);
            }
        };
        hamburgerMenu.setBounds(130, 10, 60, 80); // Positioned next to the logo
        headerPanel.add(hamburgerMenu);

        // Title
        JLabel titleLabel = new JLabel("Compare reactions for " + currentPitch.getName());
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24)); // Adjusted font size
        titleLabel.setBounds(200, 35, 1000, 30);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(null);
        centerPanel.setBackground(Color.WHITE);

        // Persona 1
        JLabel persona1Avatar = new JLabel(new ImageIcon(String.valueOf(persona1.getAvatar())));
        persona1Avatar.setBounds(55, 130, 93, 99);
        centerPanel.add(persona1Avatar);

        JLabel persona1Name = new JLabel(persona1.getName());
        persona1Name.setFont(new Font("Inter", Font.BOLD, 24));
        persona1Name.setBounds(163, 150, 300, 30);
        centerPanel.add(persona1Name);

        JScrollPane persona1Response = createDynamicTextPane("Loading " + persona1.getName() + "'s response...");
        persona1Response.setBounds(55, 230, 634, 200);
        centerPanel.add(persona1Response);

        // Persona 2
        JLabel persona2Avatar = new JLabel(new ImageIcon(String.valueOf(persona2.getAvatar())));
        persona2Avatar.setBounds(750, 130, 93, 99);
        centerPanel.add(persona2Avatar);

        JLabel persona2Name = new JLabel(persona2.getName());
        persona2Name.setFont(new Font("Inter", Font.BOLD, 24));
        persona2Name.setBounds(858, 150, 300, 30);
        centerPanel.add(persona2Name);

        JScrollPane persona2Response = createDynamicTextPane("Loading " + persona2.getName() + "'s response...");
        persona2Response.setBounds(750, 230, 634, 200);
        centerPanel.add(persona2Response);

        // Comparison Text
        JScrollPane comparisonResponse = createDynamicTextPane("Loading comparison...");
        comparisonResponse.setBounds(56, 450, 1328, 200);
        centerPanel.add(comparisonResponse);

        // Load responses asynchronously
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private String response1;
            private String response2;
            private String comparison;

            @Override
            protected Void doInBackground() throws Exception {
                response1 = fetchPersonaResponse(persona1);
                response2 = fetchPersonaResponse(persona2);
                comparison = fetchComparisonResponse(response1, response2);
                return null;
            }

            @Override
            protected void done() {
                try {
                    JTextPane persona1Text = (JTextPane) persona1Response.getViewport().getView();
                    JTextPane persona2Text = (JTextPane) persona2Response.getViewport().getView();
                    JTextPane comparisonText = (JTextPane) comparisonResponse.getViewport().getView();

                    persona1Text.setText(response1);
                    persona2Text.setText(response2);
                    comparisonText.setText(comparison);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();

        return centerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);

        // Back Button
        Button backButton = new Button("Back to Pitch Page");
        backButton.addActionListener(e -> {
            dispose();
            new ProjectPage(currentPitch).setVisible(true);
        });
        footerPanel.add(backButton);

        return footerPanel;
    }

    private JScrollPane createDynamicTextPane(String placeholder) {
        JTextPane textPane = new JTextPane();
        textPane.setText(placeholder);
        textPane.setFont(new Font("Inter", Font.PLAIN, 18));
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    private String fetchPersonaResponse(Persona persona) throws Exception {
        String systemMessage = "You are " + persona.getName() +  ","+ persona.getAge() + "," + persona.getInterests()+ ". Describe your reaction to the pitch " +
                currentPitch.getName() + ":\n" + currentPitch.getDescription();
        return application.services.chatgptapi.getResponse(List.of(new domain.models.ChatMessage("system", systemMessage)));
    }

    private String fetchComparisonResponse(String response1, String response2) throws Exception {
        String systemMessage = "Compare the following responses:\nResponse 1:\n" + response1 +
                "\nResponse 2:\n" + response2;
        return application.services.chatgptapi.getResponse(List.of(new domain.models.ChatMessage("system", systemMessage)));
    }
}
