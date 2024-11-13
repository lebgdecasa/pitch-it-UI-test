// application/services/ChatService.java
package application.services;

import domain.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private List<ChatMessage> chatHistory;

    public ChatService() {
        chatHistory = new ArrayList<>();
    }

    public void addMessage(ChatMessage message) {
        chatHistory.add(message);
    }

    public List<ChatMessage> getChatHistory() {
        return new ArrayList<>(chatHistory);
    }

    public ChatMessage sendMessage(String messageText, String personalityName) {
        // Add user's message
        ChatMessage userMessage = new ChatMessage("user", messageText);
        addMessage(userMessage);

        // TODO: Implement interaction with GPT API to get response
        // For now, we'll use a placeholder response
        String responseText = "This is a placeholder response from " + personalityName + ".";

        ChatMessage responseMessage = new ChatMessage("personality", responseText);
        addMessage(responseMessage);

        return responseMessage;
    }
}
