// application/services/chatgptapi.java
package application.services;

import domain.models.ChatMessage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

import domain.models.Persona;
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

    private static JSONObject normalizeKeys(JSONObject jsonObject) {
        JSONObject normalized = new JSONObject();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            String lowerKey = key.toLowerCase().replaceAll("\\s+", "");

            if (value instanceof JSONObject) {
                value = normalizeKeys((JSONObject) value);
            } else if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                JSONArray newArray = new JSONArray();
                for (int i = 0; i < array.length(); i++) {
                    Object arrayElement = array.get(i);
                    if (arrayElement instanceof JSONObject) {
                        arrayElement = normalizeKeys((JSONObject) arrayElement);
                    }
                    newArray.put(arrayElement);
                }
                value = newArray;
            }
            normalized.put(lowerKey, value);
        }
        return normalized;
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

    public static List<Persona> generatePersonas(String targetAudience, String projectDescription) throws IOException, InterruptedException {
        final String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing. Please set the OPENAI_API_KEY environment variable.");
        }

        // Construct the system prompt
        String systemPrompt = "You are a marketing expert. Based on the following target audience and project description, create a persona for each audience category and one extra persona that wouldn't be attracted to the product but the differences should be subtle.\n\nFor each persona, provide the following details with the exact field names and capitalization as shown below:\n\n" +
                "- Name\n" +
                "- Age\n" +
                "- Occupation\n" +
                "- Interests\n" +
                "- Salary Range\n" +
                "- Education\n" +
                "- About (this section should be a paragraph that describes who the persona is and what they're like)\n" +
                "- Market Statistics (this should be a paragraph detailing the potential market size and capital depending on the previous criteria)\n\n" +
                "Present the personas in JSON format as an array, ensuring that all field names are capitalized as shown in the example below:\n\n" +
                "Example:\n" +
                "[\n" +
                "  {\n" +
                "    \"Name\": \"John Doe\",\n" +
                "    \"Age\": 30,\n" +
                "    \"Occupation\": \"Software Engineer\",\n" +
                "    \"Interests\": [\"Coding\", \"Reading\"],\n" +
                "    \"Salary Range\": \"70,000 - 100,000 USD\",\n" +
                "    \"Education\": \"Bachelor's Degree in Computer Science\",\n" +
                "    \"About\": \"John is a tech enthusiast who loves to code and read...\",\n" +
                "    \"Market Statistics\": \"There are over a million software engineers...\"\n" +
                "  }\n" +
                "]";

        // Prepare the messages
        JSONArray messagesArray = new JSONArray();

        // System message
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", systemPrompt);
        messagesArray.put(systemMessage);

        // User message
        String userMessageContent = "Target Audience:\n" + targetAudience + "\n\nProject Description:\n" + projectDescription;

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", userMessageContent);
        messagesArray.put(userMessage);

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

        // Parse the assistant's reply to extract personas
        List<Persona> personas = parsePersonasFromResponse(assistantReply);

        return personas;
    }

    private static List<Persona> parsePersonasFromResponse(String response) {
        List<Persona> personas = new ArrayList<>();
        try {
            JSONArray personasArray = new JSONArray(response);

            for (int i = 0; i < personasArray.length(); i++) {
                JSONObject personaJSON = personasArray.getJSONObject(i);

                // Normalize keys before parsing
                JSONObject normalizedPersonaJSON = normalizeKeys(personaJSON);

                Persona persona = Persona.fromJSON(normalizedPersonaJSON);
                personas.add(persona);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return personas;
    }



}
