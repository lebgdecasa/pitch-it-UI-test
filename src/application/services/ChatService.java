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

        String responseText;
        try{
            responseText = chatgptapi.getResponse(chatHistory);
        } catch (Exception e){
            responseText = "I'm sorry, I couldn't process your request";
        }

        ChatMessage responseMessage = new ChatMessage("personality", responseText);
        addMessage(responseMessage);

        return responseMessage;
    }

    public void setSystemMessage(String systemMessage) {
        ChatMessage systemMsg = new ChatMessage("system", systemMessage);
        addMessage(systemMsg);
    }
}
