package com.flipkart.fdsg.planning.ip.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;


public class Constants {

     public static final String AUTHN_CLIENT_ID="authN-clientId";
    public static final String AUTHN_CLIENT_SECRET_KEY="authN-clientSecret";
    public static final String AUTHN_URL="authN-url";
    public static final String AUTHN_MAX_CONNECTION="authN-maxConnection";
    public static final String AUTHN_CONNECTION_TIMEOUT="authN-connectTimeOut";
    public static final String AUTHN_READ_TIMEOUT="authN-readTimeOut";

    public static class GmailConstants {
        public static final String GMAIL_INCIDENT_FLOW_EMAIL_ID = "fk.hack.incident.flow@gmail.com";
        public static final String GMAIL_INCIDENT_FLOW_APP_PASSWORD = "maaditcvqcgigvaz";
    }
}
