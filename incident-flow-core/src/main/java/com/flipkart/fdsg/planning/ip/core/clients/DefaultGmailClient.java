package com.flipkart.fdsg.planning.ip.core.clients;

import com.flipkart.fdsg.planning.ip.core.dtos.MessageDTO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static com.flipkart.fdsg.planning.ip.core.Constants.GmailConstants.GMAIL_INCIDENT_FLOW_APP_PASSWORD;
import static com.flipkart.fdsg.planning.ip.core.Constants.GmailConstants.GMAIL_INCIDENT_FLOW_EMAIL_ID;
public class DefaultGmailClient implements GmailClient {
    private String username = GMAIL_INCIDENT_FLOW_EMAIL_ID;
    private String password = GMAIL_INCIDENT_FLOW_APP_PASSWORD;
    private Session session;

    public DefaultGmailClient() {
        initializeSession();
    }

    private void initializeSession() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        session = Session.getDefaultInstance(properties);
    }


    public List<MessageDTO> getMessages(String givenEmail, long timestamp) {
        return getMessagesWithCriteria(new EmailAddressTerm(givenEmail), timestamp);
    }

    private List<MessageDTO> getMessagesWithCriteria(SearchTerm searchTerm, long timestamp) {
        List<MessageDTO> messages = new ArrayList<>();
        Store store;

        try {
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            Folder inbox = store.getFolder("inbox");
            Folder sent = store.getFolder("[Gmail]/Sent Mail");

            inbox.open(Folder.READ_ONLY);
            sent.open(Folder.READ_ONLY);

            Message[] inboxMessages = inbox.search(searchTerm);
            Message[] sentMessages = sent.search(searchTerm);

            for (Message message : inboxMessages) {
                if (message.getSentDate().getTime() > timestamp) {
                    messages.add(processMessageDTO(message));
                }
            }

            for (Message message : sentMessages) {
                if (message.getSentDate().getTime() > timestamp) {
                    messages.add(processMessageDTO(message));
                }
            }

            inbox.close(false);
            sent.close(false);
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return messages;
    }

    private static class EmailAddressTerm extends SearchTerm {
        private final String email;

        public EmailAddressTerm(String email) {
            this.email = email;
        }

        @Override
        public boolean match(Message message) {
            try {
                Address[] fromAddresses = message.getFrom();
                Address[] toAddresses = message.getRecipients(Message.RecipientType.TO);

                if (containsEmailAddress(fromAddresses, email) || containsEmailAddress(toAddresses, email)) {
                    return true;
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return false;
        }

        private boolean containsEmailAddress(Address[] addresses, String email) {
            if (addresses != null) {
                for (Address address : addresses) {
                    if (address.toString().contains(email)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private MessageDTO processMessageDTO(Message message) {
        try {
            String from = InternetAddress.toString(message.getFrom());

            Address[] toAddresses = message.getRecipients(Message.RecipientType.TO);
            List<String> toList = new ArrayList<>();
            if (toAddresses != null) {
                for (Address toAddress : toAddresses) {
                    toList.add(toAddress.toString());
                }
            }

            String subject = message.getSubject();
            String content = getTextFromMessage(message);
            String messageId = Long.toString(message.getSentDate().getTime());
            String threadId = getThreadId(message);

            return new MessageDTO(toList, from, subject, content, messageId, threadId);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) message.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bodyPart = mp.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    break; // Stop when the first text/plain part is found
                }
            }
        }
        return result;
    }

    private String getThreadId(Message message) throws MessagingException {
        String[] references = message.getHeader("References");
        String[] inReplyTo = message.getHeader("In-Reply-To");

        if (references != null && references.length > 0) {
            return extractThreadId(references[0]);
        } else if (inReplyTo != null && inReplyTo.length > 0) {
            return extractThreadId(inReplyTo[0]);
        } else {
            return Long.toString(message.getSentDate().getTime());
        }
    }

    private String extractThreadId(String headerValue) {
        String[] parts = headerValue.split("\\s+");
        if (parts.length > 0) {
            return parts[0];
        }
        return "Not Available";
    }

    public static void main(String[] args) {
        DefaultGmailClient defaultGmailClient = new DefaultGmailClient();

        List<MessageDTO> messages = defaultGmailClient.getMessages("mlokesh.mamta@gmail.com", 0l);
        for (MessageDTO message : messages) {
            System.out.println("From: " + message.getFrom());
            System.out.println("To: " + message.getToList());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Content: " + message.getContent());
            System.out.println("Message ID: " + message.getMessageId());
            System.out.println("Thread ID: " + message.getThreadId());
            System.out.println("------------------------");
        }
    }
}