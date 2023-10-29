package com.flipkart.fdsg.planning.ip.core.services;

public interface ChatGptService {
    String getResponse(String userInput);

    String getDescription(String body);

    String getOncallStatus(Boolean isFirstMsg, String body);

    String getStatus(String body);

    String getPriority(String body);

    String getSummary(String body);
}
