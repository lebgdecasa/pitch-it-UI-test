// ui/components/PitchButton.java
package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PitchButton extends JButton {

    public PitchButton(String text) {
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
