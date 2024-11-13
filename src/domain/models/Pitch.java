// domain/models/Pitch.java
package domain.models;

import java.awt.Image;

public class Pitch {
    private final String name;
    private final String description;
    private final Image image;
    private String targetAudience;
    // You can use a different type if preferred

    // Constructor
    public Pitch(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.targetAudience = "Foodies - 25%\nSnack Enthusiasts - 20%\nPickle Lovers - 25%\nHealth-Conscious - 15%\nMichelin Inspectors - 15%";
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }
    // Setters (if needed)
}
