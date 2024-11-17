// application/services/PersonaService.java
package application.services;

import domain.models.Persona;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonaService {
    private static PersonaService instance;
    private final List<Persona> personas;

    private PersonaService() {
        personas = new ArrayList<>();
        // Initialize with detailed personas
    }

    public static PersonaService getInstance() {
        if (instance == null) {
            instance = new PersonaService();
        }
        return instance;
    }

    public List<Persona> getAllPersonas() {
        return new ArrayList<>(personas);
    }

    public static List<Persona> generatePersonas(String targetAudience, String projectDescription) throws Exception {
        // Call chatgptapi to generate personas
        List<Persona> personas = chatgptapi.generatePersonas(targetAudience, projectDescription);
        return personas;
    }

}
