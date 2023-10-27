package com.flipkart.fdsg.planning.ip.core.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultChatGptService implements ChatGptService {

    private static final String API_KEY = "5fe1bffcc88747a9b7c1e8df0f64ff3f";
    private static final String API_ENDPOINT = "https://fkopenai.openai.azure.com:443/openai/deployments/gpt-35-turbo/chat/completions?api-version=2023-03-15-preview";

    public static void main(String[] args) {
        String userInput = "tell me healthy diet plan.";
        DefaultChatGptService chatGptService = new DefaultChatGptService();
        String responseContent = chatGptService.getResponse(userInput);
        System.out.println(responseContent);
    }

    public String getResponse(String userInput) {
        try {
            URL url = new URL(API_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("api-key", API_KEY);
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"messages\": [\n" +
                    "        {\n" +
                    "            \"role\": \"user\",\n" +
                    "            \"content\": \"" + userInput + "\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonPayload);
            }

            int responseCode = connection.getResponseCode();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Parse the JSON response and extract the "content" field
                String jsonResponse = response.toString();
                int startIndex = jsonResponse.indexOf("\"content\":\"") + 11;
                int endIndex = jsonResponse.indexOf("\"}", startIndex);
                String contentField = jsonResponse.substring(startIndex, endIndex);

                return contentField;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while making API call";
        }
    }
}
