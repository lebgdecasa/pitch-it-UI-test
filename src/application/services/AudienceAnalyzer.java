package application.services;

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
        String systemMessage = "in less than 15 words, give me the target audience's age, gender, and hobby preferences";
        // Call getResponse method from chatgptapi and return the response
        return chatgptapi.getResponse(userMessage, systemMessage);
    }

    public static String detailedTA(String userMessage) throws Exception {
        // Define the system message
        String systemMessage = "give me an analysis for the target audience";
        // Call getResponse method from chatgptapi and return the response
        return chatgptapi.getResponse(userMessage, systemMessage);
    }
}