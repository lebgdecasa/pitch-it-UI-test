// ui/components/TextAreaField.java
package ui.components;

import javax.swing.*;
import java.awt.*;

public class TextAreaField extends JTextArea {
    public TextAreaField() {
        initializeUI();
    }

    private void initializeUI() {
        setBackground(new Color(0xE3E3E3));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setFont(new Font("Inter", Font.PLAIN, 16));
        setLineWrap(true);
        setWrapStyleWord(true);
    }
}
