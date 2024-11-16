// application/services/AudienceAnalyzer.java
package application.services;

import domain.models.ChatMessage;
import java.util.ArrayList;
import java.util.List;

public class AudienceAnalyzer {

    // Private constructor to prevent instantiation
    private AudienceAnalyzer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Analyzes the target audience based on the provided user message.
     * @param userMessage The message content from the user.
     * @return The analyzed target audience.
     * @throws Exception If an error occurs during the API call.
     */
    public static String analyzeAudience(String userMessage) throws Exception {
        // Define the system message
        String systemMessage = "Based on the name and description of this project, I want you to give me a list of five " +
                "categories of people that would be interested in this project. Here is an example and how to structure:\n" +
                "- Foodies;\n" +
                "- Snack Enthusiasts;\n" +
                "- Pickle Lovers;\n" +
                "- Health-Conscious;\n" +
                "- Construction workers;\n" +
                "Your output must only contain the list, nothing else.";

        // Create a list of ChatMessages
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemMessage));
        messages.add(new ChatMessage("user", userMessage));

        // Call getResponse method from chatgptapi and return the assistant's reply
        String assistantReply = chatgptapi.getResponse(messages);

        return assistantReply.trim();
    }

    /**
     * Provides a detailed analysis of the target audience.
     * @param userMessage The message content from the user.
     * @return The detailed target audience analysis.
     * @throws Exception If an error occurs during the API call.
     */
    public static String detailedTA(String userMessage) throws Exception {
        // Define the system message
        String systemMessage = "Give me an analysis for the target audience based on the following information.";

        // Create a list of ChatMessages
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemMessage));
        messages.add(new ChatMessage("user", userMessage));

        // Call getResponse method from chatgptapi and return the assistant's reply
        String assistantReply = chatgptapi.getResponse(messages);

        return assistantReply.trim();
    }
}
