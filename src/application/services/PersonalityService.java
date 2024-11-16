// application/services/PersonalityService.java
package application.services;

import domain.models.Personality;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonalityService {
    private static PersonalityService instance;
    private final List<Personality> personalities;

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
                "Elena Romero is a sustainable investing advocate and former Silicon Valley venture capitalist. She built her wealth through early investments in green technology and renewable energy. Thoughtful and idealistic, Elena values business ideas with environmental and societal impact. In pitches, she urges founders to consider sustainability, aligning her support with projects that prioritize ethical practices and long-term positive change.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/elena-romero.png")))
        ));
        personalities.add(new Personality(
                "Raj Patel",
                "Raj Patel is a fintech pioneer who founded a leading digital payment platform, transforming the way we transact online. Known for his calm, analytical approach, Raj focuses on data, scalability, and clear profitability. He values pitches with strong financial foundations and realistic growth models, offering insightful feedback and investment if the numbers show real promise. Raj is precise, calculated, and driven by logical assessment.",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/raj-patel.png")))
        ));
        personalities.add(new Personality(
                "Jensen Huang",
                "Jensen Huang is the visionary founder and CEO of NVIDIA, a company that revolutionized graphics processing and artificial intelligence. Known for his innovative mindset and strategic brilliance, Jensen thrives on cutting-edge technology and ambitious ideas. In pitches, he seeks projects with groundbreaking potential, focusing on tech-driven scalability and real-world impact. Analytical yet inspiring, he values bold visions backed by solid technical foundations.",
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
