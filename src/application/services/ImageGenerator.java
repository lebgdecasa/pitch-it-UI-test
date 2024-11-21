package application.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageGenerator {

    // Define the API URL and key
    private static final String API_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    public static void main(String[] args) {
        try {
            // Parameters for the image generation
            String model = "dall-e-3";
            String prompt = "Create a vibrant and motivational advertisement for 'FitFuel' energy bars targeting health-conscious professionals like Emma Thompson, a 28-year-old marketing manager. Depict Emma in a modern gym setting, wearing stylish fitness attire, energetically holding a FitFuel chocolate almond energy bar. Highlight the natural ingredients by showcasing fresh nuts and dark chocolate in the background. Include a close-up of the FitFuel bar to emphasize its texture and packaging. Incorporate the FitFuel logo at the top right corner with the tagline 'Fuel Your Fitness Journey' beneath it. Use dynamic colors like bright greens and bold oranges to convey energy and health, and ensure the overall design is clean, high-tech, and inspirational.";
            int n = 1; // Number of images to generate
            String size = "1024x1024";

            // Create the JSON payload
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(new ImageGenerationRequest(model, prompt, n, size));

            // Create the HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            // Send the request and handle the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the response to extract the image URL
                JsonNode jsonResponse = objectMapper.readTree(response.body());
                String imageUrl = jsonResponse.get("data").get(0).get("url").asText();
                System.out.println("Generated Image URL: " + imageUrl);
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Class representing the request payload
    static class ImageGenerationRequest {
        public String model;
        public String prompt;
        public int n;
        public String size;

        public ImageGenerationRequest(String model, String prompt, int n, String size) {
            this.model = model;
            this.prompt = prompt;
            this.n = n;
            this.size = size;
        }
    }
}
