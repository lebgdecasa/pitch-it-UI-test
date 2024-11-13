// application/services/PersonalityService.java
package application.services;

import domain.models.Personality;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonalityService {
    private static PersonalityService instance;
    private List<Personality> personalities;

    private PersonalityService() {
        personalities = new ArrayList<>();

        // Initialize with some personalities
        personalities.add(new Personality(
                "Mark Cuban",
                "Mark Cuban is a billionaire entrepreneur and Dallas Mavericks owner known for his bold style on Shark Tank. After selling Broadcast.com, he became an influential investor across tech, sports, and entertainment. Mark is direct, competitive, and growth-focused. He values clear, scalable ideas and isn’t afraid to call out weaknesses, backing only pitches that show true potential for innovation and success.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/mark-cuban.png")))
        ));
        personalities.add(new Personality(
                "Elena Romero",
                "A seasoned investor.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/elena-romero.png")))
        ));
        personalities.add(new Personality(
                "Raj Patel",
                "A tech guru.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/raj-patel.png")))
        ));
        personalities.add(new Personality(
                "Jensen Huang",
                "A marketing expert.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/jensen-huang.png")))
        ));
    }

    public static PersonalityService getInstance() {
        if (instance == null) {
            instance = new PersonalityService();
        }
        return instance;
    }

    public List<Personality> getAllPersonalities() {
        return new ArrayList<>(personalities);
    }
}
