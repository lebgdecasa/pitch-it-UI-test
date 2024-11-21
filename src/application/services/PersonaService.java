// application/services/PersonaService.java
package application.services;

import domain.models.Persona;
import domain.models.Pitch;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

public class PersonaService {
    private static PersonaService instance;

    private final Map<Pitch, List<Persona>> pitchPersonas;

    private PersonaService() {
        pitchPersonas = new HashMap<>();
        // Initialize with detailed personas
    }

    public static PersonaService getInstance() {
        if (instance == null) {
            instance = new PersonaService();
        }
        return instance;
    }

    public List<Persona> getPersonasForPitch(Pitch pitch) {
        return pitchPersonas.getOrDefault(pitch, new ArrayList<>());
    }

    public static List<Persona> generatePersonas(String targetAudience, String projectDescription, Pitch pitch) throws Exception {
        // Call chatgptapi to generate personas
        return chatgptapi.generatePersonas(targetAudience, projectDescription, pitch);
    }

}
