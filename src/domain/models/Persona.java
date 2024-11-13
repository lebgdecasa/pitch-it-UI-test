// domain/models/Persona.java
package domain.models;

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

    // Constructor
    public Persona(String name, String description, String favoriteFood, int age, String hometown, String hobby, String about, String stats, String quote) {
        this.name = name;
        this.description = description;
        this.favoriteFood = favoriteFood;
        this.age = age;
        this.hometown = hometown;
        this.hobby = hobby;
        this.about = about;
        this.stats = stats;
        this.quote = quote;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    // ... other getters ...

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
}
