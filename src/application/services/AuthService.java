// application/services/AuthService.java
package application.services;

import domain.models.User;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static final Map<String, String> userDatabase = new HashMap<>();

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
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }
}
