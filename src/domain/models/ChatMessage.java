// domain/models/ChatMessage.java
package domain.models;

public class ChatMessage {
    private String sender; // "user" or "personality"
    private String message;

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    // Getters
    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
