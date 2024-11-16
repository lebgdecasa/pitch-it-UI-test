package application.services;

import domain.models.ChatMessage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;
import org.json.*;

public class chatgptapi {

    private static final String LOG_FILE_PATH = "api_calls.txt";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static String getResponse(List<ChatMessage> messages) throws IOException, InterruptedException {
        final String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing. Please set the OPENAI_API_KEY environment variable.");
        }

        // Construct the messages array for the API request
        JSONArray messagesArray = new JSONArray();
        for (ChatMessage message : messages) {
            JSONObject messageObject = new JSONObject();
            String role = message.getSender().equalsIgnoreCase("system") ? "system"
                    : message.getSender().equalsIgnoreCase("user") ? "user" : "assistant";
            messageObject.put("role", role);
            messageObject.put("content", message.getMessage());
            messagesArray.put(messageObject);
        }

        // JSON request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4");
        requestBody.put("messages", messagesArray);

        // Create HTTP request
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        // Send the request and get the response
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Log the input and output to a file
        logApiCall(requestBody.toString(), response.body());

        // Extract the assistant's reply from the response JSON
        String assistantReply = parseAssistantReply(response.body());

        return assistantReply;
    }

    private static String parseAssistantReply(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);
            JSONArray choices = json.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject message = choices.getJSONObject(0).getJSONObject("message");
                return message.getString("content").trim();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "I'm sorry, I couldn't understand the response.";
    }

    private static void logApiCall(String input, String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("API Input: " + input);
            writer.newLine();
            writer.write("API Output: " + output);
            writer.newLine();
            writer.write("----------------------------------------------------");
            writer.newLine();
        } catch (IOException error) {
            System.out.println("Error logging API call: " + error.getMessage());
        }
    }
}
