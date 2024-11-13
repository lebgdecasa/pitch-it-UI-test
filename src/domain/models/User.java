// domain/models/User.java
package domain.models;

public class User {
    private final String username;

    // Constructor
    public User(String username, String password) {
        this.username = username;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    // It's generally good practice not to have a getter for password

    // Setters
    // You can omit setters if you want immutable objects
}
