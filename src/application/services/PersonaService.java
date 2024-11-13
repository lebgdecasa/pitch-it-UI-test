// application/services/PersonaService.java
package application.services;

import domain.models.Persona;

import java.util.ArrayList;
import java.util.List;

public class PersonaService {
    private static PersonaService instance;
    private final List<Persona> personas;

    private PersonaService() {
        personas = new ArrayList<>();
        // Initialize with detailed personas
        personas.add(new Persona(
                "Stu - The Pickle Enthusiast",
                "Passionate about all things pickled.",
                "Pickles",
                25,
                "Paris",
                "Trying Pickles",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
                "Some stats here...",
                "Pickles are life, pickles are love"
        ));
        // ... add other personas ...
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
}
