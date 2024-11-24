// domain/models/Pitch.java
package domain.models;

import application.services.AudienceAnalyzer;
import application.services.ImageAnalyzer;
import ui.VisionPage;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pitch {
    private final String name;
    private final String description;
    private final Image image;
    private final String targetAudience;
    private Map<String, DetailedTargetAudience> detailedTAMap;
    private List<Persona> personas;
    // You can use a different type if preferred

    // Constructor
    public Pitch(String name, String description, Image image) throws Exception {
        this.name = name;
        this.description = description;
        this.image = image;
        this.targetAudience = AudienceAnalyzer.analyzeAudience(name + description);
        this.detailedTAMap = new HashMap<>();
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

    public Map<String, DetailedTargetAudience> getDetailedTAMap() {
        return detailedTAMap;
    }

    public void setDetailedTAMap(Map<String, DetailedTargetAudience> detailedTAMap) {
        this.detailedTAMap = detailedTAMap;
    }

    // Setters (if needed)
}
