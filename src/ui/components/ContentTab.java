// ui/components/ContentTab.java
package ui.components;

import javax.swing.*;
import java.awt.*;

public class ContentTab extends JPanel {

    public ContentTab(String text) {
        initializeUI(text);
    }

    private void initializeUI(String text) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.PLAIN, 32));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(label, BorderLayout.CENTER);
    }
}
