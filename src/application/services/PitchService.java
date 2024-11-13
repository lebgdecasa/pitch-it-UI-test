// application/services/PitchService.java
package application.services;

import domain.models.Pitch;
import java.util.ArrayList;
import java.util.List;

public class PitchService {

    private static PitchService instance;
    private final List<Pitch> pitches;

    private PitchService() {
        pitches = new ArrayList<>();
    }

    public static PitchService getInstance() {
        if (instance == null) {
            instance = new PitchService();
        }
        return instance;
    }

    public void addPitch(Pitch pitch) {
        pitches.add(pitch);
    }

    public List<Pitch> getAllPitches() {
        return new ArrayList<>(pitches);
    }
}
