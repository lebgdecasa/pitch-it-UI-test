package application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AudienceAnalyzer {

    // Private constructor to prevent instantiation
    private AudienceAnalyzer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public class ChatHelper {

        /**
         * Parses the API response JSON and extracts the "content" field.
         *
         * @param apiResponse The raw JSON response from the API.
         * @return The "content" field as a string.
         * @throws Exception If the JSON structure is invalid or the "content" field is missing.
         */
        public static String extractContent(String apiResponse) throws Exception {
            // Create an ObjectMapper to parse the JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON response
            JsonNode rootNode = objectMapper.readTree(apiResponse);

            // Navigate to the "content" field in the response
            JsonNode contentNode = rootNode.at("/choices/0/message/content");

            // Ensure the "content" field exists
            if (contentNode.isMissingNode()) {
                throw new IllegalArgumentException("Invalid response format: 'content' field not found");
            }

            // Return the extracted content
            return contentNode.asText();
        }
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
        String jsonResponse = chatgptapi.getResponse(userMessage, systemMessage);
        return ChatHelper.extractContent(jsonResponse);
    }

    public static String detailedTA(String userMessage) throws Exception {
        // Define the system message
        String systemMessage = "give me an analysis for the target audience";
        // Call getResponse method from chatgptapi and return the response
        String jsonResponse =  chatgptapi.getResponse(userMessage, systemMessage);
        return ChatHelper.extractContent(jsonResponse);
    }
}