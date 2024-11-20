// domain/models/Pitch.java
package domain.models;

import application.services.AudienceAnalyzer;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Pitch {
    private final String name;
    private final String description;
    private final Image image;
    private String targetAudience;
    private String detailedTA;
    private List<Persona> personas;
    // You can use a different type if preferred

    // Constructor
    public Pitch(String name, String description, Image image) throws Exception {
        this.name = name;
        this.description = description;
        this.image = image;
        this.targetAudience = AudienceAnalyzer.analyzeAudience(name + description);
        this.detailedTA = AudienceAnalyzer.detailedTA(name + description);
        this.personas = new ArrayList<>();
    }

    // Getters

    public List<Persona> getPersonas() {
        return personas;
    }
    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

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

    public void setDetailedTA() throws Exception {
        this.detailedTA = AudienceAnalyzer.detailedTA(name);
    }

    // Setters (if needed)
}
