package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.hadoop.util.StringUtils.format;

@Slf4j
public class DefaultChatGptService implements ChatGptService {

    private static final String API_KEY = "5fe1bffcc88747a9b7c1e8df0f64ff3f";
    private static final String API_ENDPOINT = "https://fkopenai.openai.azure.com:443/openai/deployments/gpt-35-turbo/chat/completions?api-version=2023-05-15";

    private static final String ONLY_ANSWER = "Don't give explanation of your answer. Just give me answer that's it, no header no footer, only answer. ";
    private static final String STARTING_INTRO = "Following is the email thread of oncall issue. ";

    private static final String DESCRIPTION = "By reading the oncall issue email thread. Summarize this issue under 15 to 20 words. Give crisp response. It should be readable by non-tech person as well. Don't print json in it. ";

    private static final String ONCALL_STATUS = "By reading the oncall issue email thread. Give me its status in not more than 6 words. Status can be for example: " +
            "waiting for ack (if no one has replyed to the message yet.), if it's the first message of the thread, " +
            "waiting for resolution (if folks have replyed, but still resolution need to be provided, or if body contains substring 'Acknowledged'), " +
            "retriggred the model (if folks has retriggered the failed model)," +
            "resolution provided (if resolution is provided)," +
            "wating for reply from <> (if someone is tagged for reply), " +
            "Don't restrict yourself to these status only, rather give meaningful status in 3 - 4 words, that will tell me overall status of the issue. " +
            "If no one has replyed to the first message, then status should be 'Waiting for Acknowledgement'. You will get idea whether someone has responed or not if there is '>' symbol. ";

    private static final String PREVIOUS_STATUS = "Previous Status was : %s";

    private static final String STATUS = "By reading the oncall issue email thread. Give me its status. Just tell me either it is \'ACTIVE\' or \'CLOSED\'. Tell me CLOSED if you feel resolution of the issue is provided and the ticket is no longer need to be active. Acknowledged doesn't mean it is CLOSED, it means someone have seen the mail and he is working on it now, so be mindful. ";

    private static final String PRIORITY = "By reading the oncall issue email thread. Tell me what is its priority. Prioirity can be one of : P0, P1, P2, P3." +
             "P0 being the highest priority. If you can't figure it out simply tell P1. ";

    private static final String SUMMARY = "By reading the oncall issue email thread. Summarize the issue in not more than 150 words. ";

    private static final String EMAIL_BODY = "Email Body: ";

    public static void main(String[] args) {
        String userInput = "Following is the email thread of oncall issue. Summarize this issue under 25 words. Pipeline Failure Name : SAIL_mint_14a_audio_regPipeline failed at TRIGGER_DSP stepTRIGGER_DSP Details :http://10.83.32.228:9090/dsp-ui/html/index.html?request_id=8770026<https://protect-eu.mimecast.com/s/LOQHCnYPLSlVPOWgf9CN_X?domain=10.83.32.228>Error:{\"requestId\":5785607397640454154,\"requestStatus\":\"FAILED\",\"message\":\"WorkflowGroup Execution Terminated!\"Don't give explanation of your answer. Just give me answer that's it, no header no footer, only answer. ";
        DefaultChatGptService chatGptService = new DefaultChatGptService();
        String responseContent = chatGptService.getResponse(userInput.replace("\"", "'"));
        System.out.println(responseContent);
    }

    public static String removeSpecialCharacters(String input) {
        // Define the pattern for special characters
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        // Use the pattern to replace special characters with an empty string
        return pattern.matcher(input).replaceAll("");
    }

    public String getResponse(String userInput) {
        try {
            userInput = userInput.replace("\"", "'");
            log.info("Making API call with user input: {}", userInput);
            URL url = new URL(API_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("api-key", API_KEY);
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    \"messages\": [\n" +
                    "        {\n" +
                    "            \"role\": \"system\",\n" +
                    "            \"content\": \"Assistant is a large language model trained by OpenAI.\"\n" +
                    "        },\n" +
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
            log.info("API call response code: {}", responseCode);

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

                log.info("API response content: {}", contentField);
                return contentField.replace("API response content: ", "");
            }

        } catch (Exception e) {
            log.error("Error occurred while making API call", e);
            return "Error occurred while making API call";
        }
    }

    @Override
    public String getDescription(String body) {
        String request = STARTING_INTRO + DESCRIPTION + EMAIL_BODY + body + ONLY_ANSWER;
        log.info("Getting description with request: {}", request);
        return getResponse(request);
    }

    @Override
    public String getOncallStatus(Boolean isFirstMsg, String body) {
        if(isFirstMsg) {
            return "Waiting For ACK";
        }
        String request = STARTING_INTRO + ONCALL_STATUS + EMAIL_BODY + body + ONLY_ANSWER;
        log.info("Getting oncall status with request: {}", request);
        return getResponse(request);
    }

    @Override
    public String getStatus(String body) {
        String request = STARTING_INTRO + STATUS + EMAIL_BODY +  body + ONLY_ANSWER;
        log.info("Getting status with request: {}", request);
        String response = getResponse(request);
        return extractStatus(response);
    }
    private String extractStatus(String response) {
        if(response.contains("CLOSED")) {
            return "CLOSED";
        } else {
            return "ACTIVE";
        }
    }


    @Override
    public String getPriority(String body) {
        String request = STARTING_INTRO + PRIORITY + EMAIL_BODY + body + ONLY_ANSWER;
        log.info("Getting priority with request: {}", request);
        String response = getResponse(request);
        return extractPriority(response);
    }

    private String extractPriority(String response) {
        if (response.contains("P0")) {
            return "P0";
        } else  if (response.contains("P1")) {
            return "P1";
        }  else if (response.contains("P2")) {
            return "P2";
        }  else if (response.contains("P3")) {
            return "P3";
        } else {
            return "P1";
        }
    }

    @Override
    public String getSummary(String body) {
        String request = STARTING_INTRO + SUMMARY + EMAIL_BODY + body + ONLY_ANSWER;
        log.info("Getting summary with request: {}", request);
        return getResponse(request);
    }
}
