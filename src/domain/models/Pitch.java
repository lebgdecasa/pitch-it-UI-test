// domain/models/Pitch.java
package domain.models;

import application.services.AudienceAnalyzer;

import java.awt.Image;

public class Pitch {
    private final String name;
    private final String description;
    private final Image image;
    private String targetAudience;
    private String detailedTA;
    // You can use a different type if preferred

    // Constructor
    public Pitch(String name, String description, Image image) throws Exception {
        this.name = name;
        this.description = description;
        this.image = image;
        this.targetAudience = AudienceAnalyzer.analyzeAudience(name + description);
        this.detailedTA = AudienceAnalyzer.detailedTA(name + description);
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

    public String getDetailedTA() {return detailedTA;}

    public void setTargetAudience() throws Exception {
        this.targetAudience = AudienceAnalyzer.analyzeAudience(name);
    }
    // Setters (if needed)
}
