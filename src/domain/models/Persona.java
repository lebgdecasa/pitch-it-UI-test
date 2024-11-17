// domain/models/Persona.java
package domain.models;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Persona {
    private String name;
    private String description;
    private String favoriteFood;
    private int age;
    private String hometown;
    private String hobby;
    private String about;
    private String stats;
    private String quote;
    private ImageIcon avatar;
    private String interests; // Added field to store interests as a String

    // Constructor
    public Persona(String name, String description, String favoriteFood, int age, String hometown, String hobby, String about, String stats, String quote, String interests, ImageIcon avatar) {
        this.name = name;
        this.description = description;
        this.favoriteFood = favoriteFood;
        this.age = age;
        this.hometown = hometown;
        this.hobby = hobby;
        this.about = about;
        this.stats = stats;
        this.quote = quote;
        this.interests = interests;
        this.avatar = avatar;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public int getAge() {
        return age;
    }

    public String getHometown() {
        return hometown;
    }

    public String getHobby() {
        return hobby;
    }

    public String getAbout() {
        return about;
    }

    public String getStats() {
        return stats;
    }

    public String getQuote() {
        return quote;
    }

    public ImageIcon getAvatar() {
        return avatar;
    }

    public String getInterests() {
        return interests;
    }

    // Static method to create a Persona from JSON
    public static Persona fromJSON(JSONObject jsonObject) throws MalformedURLException {
        String name = jsonObject.optString("Name", "Unknown");
        String description = jsonObject.optString("Description", "");
        String favoriteFood = jsonObject.optString("Favorite Food", "N/A");
        int age = jsonObject.optInt("Age", 25);
        String hometown = jsonObject.optString("Hometown", "Unknown");
        String hobby = jsonObject.optString("Hobby", "N/A");
        String about = jsonObject.optString("About", "");
        String stats = jsonObject.optString("Stats", "");
        String quote = jsonObject.optString("Quote", "");
        String avatarUrl = jsonObject.optString("Avatar URL", null);
        String interests = ""; // Initialize interests

        // Handle "Interests" field
        try {
            if (jsonObject.has("Interests")) {
                Object interestsObj = jsonObject.get("Interests");
                if (interestsObj instanceof JSONArray) {
                    JSONArray interestsArray = jsonObject.getJSONArray("Interests");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < interestsArray.length(); i++) {
                        sb.append(interestsArray.getString(i));
                        if (i < interestsArray.length() - 1) {
                            sb.append(", ");
                        }
                    }
                    interests = sb.toString();
                } else if (interestsObj instanceof String) {
                    interests = jsonObject.getString("Interests");
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
                // Handle invalid URL or set a default avatar
                avatar = new ImageIcon("default-avatar.png"); // Ensure this path is correct
            }
        } else {
            avatar = new ImageIcon("default-avatar.png"); // Ensure this path is correct
        }

        return new Persona(name, description, favoriteFood, age, hometown, hobby, about, stats, quote, interests, avatar);
    }
}