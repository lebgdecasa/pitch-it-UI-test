package application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageAnalyzer {

    // Private constructor to prevent instantiation
    private ImageAnalyzer() {
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
     * @param imageUrl The image URL content from the user.
     * @return The analyzed target audience.
     * @throws Exception If an error occurs during the API call.
     */
    public static String analyzeImage(String imageUrl) throws Exception {
        // Define the system message
        String question = "What’s in this image?";
        // Call getResponse method from openimagequery and return the response
        String jsonResponse = OpenImageQuery.analyzeImage(imageUrl, question);
        return ChatHelper.extractContent(jsonResponse);
    }
}
