package application.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenImageQuery {

    // Define the API URL and key
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    public static void main(String[] args) {
        try {
            // Create the payload as JSON
            final ObjectMapper objectMapper = new ObjectMapper();

            // JSON structure for messages
            final String jsonPayload = objectMapper.writeValueAsString(new Payload());

            // Create the HTTP client and request
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request and get the response
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response:");
            System.out.println(response.body());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Define the structure of the payload
    static class Payload {
        public String model = "gpt-4";
        public Message[] messages = {
                new Message("user", "What’s in this image? The image URL is: https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg")
        };
    }

    // Define the structure of each message
    static class Message {
        public String role;
        public String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
