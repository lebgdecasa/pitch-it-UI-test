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
        personas.add(new Persona(
                "Stu - The Pickle Enthusiast",
                "Passionate about all things pickled.",
                "Pickles",
                25,
                "Paris",
                "Trying Pickles",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
                "Some stats here...",
                "Pickles are life, pickles are love",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/stu.png")))
        ));

        // New Persona 1
        personas.add(new Persona(
                "Mia - The Coffee Connoisseur",
                "Lover of all brews and beans.",
                "Coffee",
                30,
                "Seattle",
                "Visiting Coffee Shops",
                "Mia has traveled the world in search of the perfect cup of coffee...",
                "Favorite Roast: Ethiopian Yirgacheffe",
                "Life begins after coffee.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/mia.png")))
        ));

        // New Persona 2
        personas.add(new Persona(
                "Liam - The Mountain Explorer",
                "Always seeking the next peak to conquer.",
                "Mountains",
                35,
                "Denver",
                "Hiking",
                "Liam spends his weekends scaling mountains and capturing breathtaking views...",
                "Highest Peak Climbed: Mount Everest",
                "The mountains are calling, and I must go.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/liam.png")))
        ));

        // New Persona 3
        personas.add(new Persona(
                "Emma - The Tech Guru",
                "In love with gadgets and coding.",
                "Technology",
                28,
                "San Francisco",
                "Programming",
                "Emma is a software engineer who enjoys building innovative applications...",
                "GitHub Repos: 150+",
                "Talk is cheap. Show me the code.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/emma.png")))
        ));

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
