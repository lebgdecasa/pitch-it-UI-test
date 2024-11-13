// ui/components/Size48.java
package ui.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class Size48 extends JLabel {
    public Size48(String iconName) {
        initializeUI(iconName);
    }

    private void initializeUI(String iconName) {
        URL iconUrl = getClass().getResource("/" + iconName);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(iconUrl));
        setIcon(icon);
        setPreferredSize(new Dimension(48, 48));
    }
}
