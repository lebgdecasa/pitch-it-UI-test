// application/services/AudienceAnalyzer.java
package application.services;

import domain.models.ChatMessage;
import domain.models.DetailedTargetAudience;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public static List<DetailedTargetAudience> getDetailedTAList(String audienceCategory) throws Exception {
        // Existing code to generate detailedTA
        // Define the system message
        String systemMessage = "Provide a detailed analysis of the target audience category \"" + audienceCategory + "\". " +
                "Structure your response in JSON format with the following fields:\n" +
                "{\n" +
                "  \"Name\": \"\",\n" +
                "  \"DemographicAttributes\": {\n" +
                "    \"MinAge\": 0,\n" +
                "    \"MaxAge\": 0,\n" +
                "    \"Gender\": \"\",\n" +
                "    \"EducationLevel\": \"\",\n" +
                "    \"Occupation\": \"\",\n" +
                "    \"IncomeLevel\": \"\",\n" +
                "    \"GeographicLocation\": \"\"\n" +
                "  },\n" +
                "  \"PsychographicAttributes\": {\n" +
                "    \"InterestsAndPassions\": [],\n" +
                "    \"Values\": [],\n" +
                "    \"PersonalityTraits\": [],\n" +
                "    \"Lifestyle\": \"\"\n" +
                "  },\n" +
                "  \"BehavioralAttributes\": {\n" +
                "    \"IsEarlyAdopter\": false,\n" +
                "    \"TechSavviness\": \"\",\n" +
                "    \"GadgetOwnership\": [],\n" +
                "    \"MediaConsumption\": [],\n" +
                "    \"OnlineEngagement\": [],\n" +
                "    \"IsInfluencer\": false\n" +
                "  },\n" +
                "  \"OtherAttributes\": {\n" +
                "    \"EventParticipation\": [],\n" +
                "    \"Hobbies\": [],\n" +
                "    \"BrandAffinity\": [],\n" +
                "    \"EnvironmentalConcerns\": false,\n" +
                "    \"GlobalPerspective\": false,\n" +
                "    \"MultilingualAbilities\": false\n" +
                "  }\n" +
                "}";

        // Create messages
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemMessage));

        // Call the API
        String assistantReply = chatgptapi.getResponse(messages);

        // Parse the assistant's reply into a DetailedTargetAudience object
        JSONObject json = new JSONObject(assistantReply.trim());
        DetailedTargetAudience detailedTA = parseDetailedTAFromJSON(json);
        List<DetailedTargetAudience> detailedTAList = new ArrayList<>();
        detailedTAList.add(detailedTA);

        return detailedTAList;
    }

    private static DetailedTargetAudience parseDetailedTAFromJSON(JSONObject json) {
        DetailedTargetAudience detailedTA = new DetailedTargetAudience(json.getString("Name"));

        // Demographic Attributes
        JSONObject demographic = json.getJSONObject("DemographicAttributes");
        detailedTA.setMinAge(demographic.getInt("MinAge"));
        detailedTA.setMaxAge(demographic.getInt("MaxAge"));
        detailedTA.setGender(demographic.getString("Gender"));
        detailedTA.setEducationLevel(demographic.getString("EducationLevel"));
        detailedTA.setOccupation(demographic.getString("Occupation"));
        detailedTA.setIncomeLevel(demographic.getString("IncomeLevel"));
        detailedTA.setGeographicLocation(demographic.getString("GeographicLocation"));

        // Psychographic Attributes
        JSONObject psychographic = json.getJSONObject("PsychographicAttributes");
        detailedTA.setInterestsAndPassions(jsonArrayToList(psychographic.getJSONArray("InterestsAndPassions")));
        detailedTA.setValues(jsonArrayToList(psychographic.getJSONArray("Values")));
        detailedTA.setPersonalityTraits(jsonArrayToList(psychographic.getJSONArray("PersonalityTraits")));
        detailedTA.setLifestyle(psychographic.getString("Lifestyle"));

        // Behavioral Attributes
        JSONObject behavioral = json.getJSONObject("BehavioralAttributes");
        detailedTA.setEarlyAdopter(behavioral.getBoolean("IsEarlyAdopter"));
        detailedTA.setTechSavviness(behavioral.getString("TechSavviness"));
        detailedTA.setGadgetOwnership(jsonArrayToList(behavioral.getJSONArray("GadgetOwnership")));
        detailedTA.setMediaConsumption(jsonArrayToList(behavioral.getJSONArray("MediaConsumption")));
        detailedTA.setOnlineEngagement(jsonArrayToList(behavioral.getJSONArray("OnlineEngagement")));
        detailedTA.setInfluencer(behavioral.getBoolean("IsInfluencer"));

        // Other Attributes
        JSONObject other = json.getJSONObject("OtherAttributes");
        detailedTA.setEventParticipation(jsonArrayToList(other.getJSONArray("EventParticipation")));
        detailedTA.setHobbies(jsonArrayToList(other.getJSONArray("Hobbies")));
        detailedTA.setBrandAffinity(jsonArrayToList(other.getJSONArray("BrandAffinity")));
        detailedTA.setEnvironmentalConcerns(other.getBoolean("EnvironmentalConcerns"));
        detailedTA.setGlobalPerspective(other.getBoolean("GlobalPerspective"));
        detailedTA.setMultilingualAbilities(other.getBoolean("MultilingualAbilities"));

        return detailedTA;
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
