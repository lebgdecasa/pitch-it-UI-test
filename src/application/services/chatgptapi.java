package application.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class chatgptapi {

    private static final String LOG_FILE_PATH = "api_calls.txt";
    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Makes an API call to get the target audience based on the provided user and system messages.
     *
     * @param userMessage The message content from the user.
     * @param systemMessage The system message content to guide the model.
     * @return The API response as a string.
     * @throws IOException if an I/O error occurs during the API call.
     * @throws InterruptedException if the operation is interrupted.
     */
    public static String getResponse(String userMessage, String systemMessage) throws IOException, InterruptedException {
        // Fetch the OpenAI API key from environment variables
        final String apiKey = System.getenv("OPENAI_API_KEY");

        // Check if the API key is null or empty
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing. Please set the OPENAI_API_KEY environment variable.");
        }

        // JSON request body with the user's input and system message
        final String body = """
                {
                    "model": "gpt-4",
                    "messages": [
                        {"role": "system",
                        "content": "%s"
                        },
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }""".formatted(systemMessage, userMessage);

        // Create HTTP request
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        // Send the request and get the response
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Log the input and output to a file
        logApiCall(userMessage, response.body());

        // Return the API response body
        return response.body();
    }

    /**
     * Logs the API call input and output to a file.
     * @param input The user's input message.
     * @param output The API response.
     */
    private static void logApiCall(String input, String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("User Input: " + input);
            writer.newLine();
            writer.write("API Output: " + output);
            writer.newLine();
            writer.write("----------------------------------------------------");
            writer.newLine();
        } catch (IOException error) {
            System.out.println("Error logging API call: " + error.getMessage());
        }
    }

    public static void main(String[] args) {
        // Optional: Test the API call directly from the main method
        try {
            String userMessage = "I'm creating a new product line. Can you help identify the audience?";
            String systemMessage = "in less than 15 words, give me the target audience's age, gender, and hobby preferences";
            String response = getResponse(userMessage, systemMessage);
            System.out.println("Target Audience: " + response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
