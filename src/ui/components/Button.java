// ui/components/Button.java
package ui.components;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    public Button(String text) {
        super(text);
        initializeUI();
    }

    private void initializeUI() {
        setFont(new Font("Inter", Font.PLAIN, 48));
        setBackground(new Color(0xECECEC));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusPainted(false);
        setContentAreaFilled(true);
    }
}
