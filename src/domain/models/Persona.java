// domain/models/Persona.java
package domain.models;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Persona {
    private String name;
    private int age;
    private String education;
    private String salaryrange;
    private String about;
    private String stats;
    private String occupation;
    private ImageIcon avatar;
    private String interests; // Added field to store interests as a String

    // Constructor
    public Persona(String name, String occupation, int age, String education, String salaryrange, String about, String stats, String interests, ImageIcon avatar) {
        this.name = name;
        this.occupation = occupation;
        this.age = age;
        this.education = education;
        this.salaryrange = salaryrange;
        this.about = about;
        this.stats = stats;
        this.avatar = avatar;
        this.interests = interests;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return education;
    }

    public int getAge() {
        return age;
    }

    public String getEducation() {
        return education;
    }

    public String getSalaryrange() {
        return salaryrange;
    }

    public String getAbout() {
        return about;
    }

    public String getStats() {
        return stats;
    }

    public ImageIcon getAvatar() {
        return avatar;
    }

    public String getInterests() {
        return interests;
    }

    // Static method to create a Persona from JSON
    public static Persona fromJSON(JSONObject jsonObject) throws MalformedURLException {
        String name = jsonObject.optString("name", "Unknown");
        String occupation = jsonObject.optString("occupation", "N/A");
        int age = jsonObject.optInt("age", 25);
        String education = jsonObject.optString("education", "Unknown");
        String salaryRange = jsonObject.optString("salaryrange", "N/A");
        String about = jsonObject.optString("about", "");
        String stats = jsonObject.optString("marketstatistics", "");
        String avatarUrl = jsonObject.optString("avatarurl", null);
        String interests = ""; // Initialize interests

        // Handle "interests" field
        try {
            if (jsonObject.has("interests")) {
                Object interestsObj = jsonObject.get("interests");
                if (interestsObj instanceof JSONArray) {
                    JSONArray interestsArray = (JSONArray) interestsObj;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < interestsArray.length(); i++) {
                        sb.append(interestsArray.getString(i));
                        if (i < interestsArray.length() - 1) {
                            sb.append(", ");
                        }
                    }
                    interests = sb.toString();
                } else if (interestsObj instanceof String) {
                    interests = (String) interestsObj;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            interests = "N/A";
        }

        // Handle avatar
        ImageIcon avatar = null;
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try {
                avatar = new ImageIcon(new URL(avatarUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                avatar = new ImageIcon("default-avatar.png"); // Ensure this path is correct
            }
        } else {
            avatar = new ImageIcon("default-avatar.png"); // Ensure this path is correct
        }

        return new Persona(name, occupation, age, education, salaryRange, about, stats, interests, avatar);
    }
}