package application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class ImageAnalyzer {

    // Private constructor to prevent instantiation
    private ImageAnalyzer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class ImageHelper {


        private ImageHelper() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }

        /**
         * Parses the API response JSON and extracts the "url" field for the generated image.
         *
         * @param apiResponse The raw JSON response from the API.
         * @return The "url" field as a string.
         * @throws Exception If the JSON structure is invalid or the "url" field is missing.
         */
        public static String extractImageUrl(String apiResponse) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(apiResponse);
            JsonNode urlNode = rootNode.at("/data/0/url");
            if (urlNode.isMissingNode()) {
                throw new IllegalArgumentException("Invalid response format: 'url' field not found");
            }
            return urlNode.asText();
        }

        /**
         * Downloads an image from a given URL and saves it to the specified file path.
         *
         * @param imageUrl The URL of the image to download.
         * @param filePath The path where the image will be saved.
         * @throws Exception If an error occurs during the download.
         */
        public static void downloadImage(String imageUrl, String filePath) throws Exception {
            HttpURLConnection connection = getHttpURLConnection(imageUrl);

            // Stream the image data to a file
            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Disconnect the connection
            connection.disconnect();
        }

        private static HttpURLConnection getHttpURLConnection(String imageUrl) throws URISyntaxException, IOException {
            URI uri = new URI(imageUrl);

            // Open a connection to the URI
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Check if the connection was successful
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to download image: HTTP response code " + connection.getResponseCode());
            }
            return connection;
        }

    }

    /**
     * Generates an image using a specific user input with DALL-E 3 and returns the local file path of the image.
     *
     * @param userInput The specific user input for the image generation.
     * @param outputFilePath The file path where the image will be saved.
     * @return The file path of the downloaded image.
     * @throws Exception If an error occurs during the API call or image download.
     */
    public static String generateAndDownloadImage(String userInput, String outputFilePath) throws Exception {
        // Generate the image URL
        String imageUrl = generateImage(userInput);

        // Download the image and save it to the specified file path
        ImageHelper.downloadImage(imageUrl, outputFilePath);

        return outputFilePath;
    }

    /**
     * Generates an image using a specific user input with DALL-E 3 and returns the image URL.
     *
     * @param userInput The specific user input for the image generation.
     * @return The URL of the generated image.
     * @throws Exception If an error occurs during the API call.
     */
    public static String generateImage(String userInput) throws Exception {
        String systemMessage = "Create a vibrant and motivational advertisement";
        String finalPrompt = systemMessage + " " + userInput;

        String jsonResponse = ImageGenerator.generateImage(finalPrompt, "dall-e-3", 1, "1024x1024");
        return ImageHelper.extractImageUrl(jsonResponse);
    }
}
