// domain/models/Personality.java
package domain.models;

import javax.swing.ImageIcon;

public class Personality {
    private String name;
    private String description;
    private ImageIcon avatar; // Or use a String for image path

    public Personality(String name, String description, ImageIcon avatar) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ImageIcon getAvatar() {
        return avatar;
    }
}
