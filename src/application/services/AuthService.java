// application/services/AuthService.java
package application.services;

import domain.models.User;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static final Map<String, String> userDatabase = new HashMap<>();
    private static AuthService instance;
    private static User currentUser;

    // Private constructor for Singleton pattern
    public AuthService() {}

    // Singleton instance getter
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean registerUser(String username, String password) {
        if (userDatabase.containsKey(username)) {
            // Username already exists
            return false;
        } else {
            userDatabase.put(username, password);
            return true;
        }
    }

    public boolean authenticateUser(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            // Authentication successful, set current user
            currentUser = new User(username);
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser == null) {
            // No user is logged in
            return false;
        }
        String username = currentUser.getUsername();
        String storedPassword = userDatabase.get(username);

        if (storedPassword.equals(oldPassword)) {
            // Old password matches, change to new password
            userDatabase.put(username, newPassword);
            return true;
        }
        // Old password does not match
        return false;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
