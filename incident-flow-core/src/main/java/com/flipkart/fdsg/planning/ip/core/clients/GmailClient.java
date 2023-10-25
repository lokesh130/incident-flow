package com.flipkart.fdsg.planning.ip.core.clients;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.*;
import java.io.IOException;
import java.util.Properties;

import static com.flipkart.fdsg.planning.ip.core.Constants.GmailConstants.GMAIL_INCIDENT_FLOW_APP_PASSWORD;
import static com.flipkart.fdsg.planning.ip.core.Constants.GmailConstants.GMAIL_INCIDENT_FLOW_EMAIL_ID;

public class GmailClient {
    private String username = GMAIL_INCIDENT_FLOW_EMAIL_ID;
    private String password = GMAIL_INCIDENT_FLOW_APP_PASSWORD;


    public void readAllUnreadMessages(int limit) {
        readMessagesWithCriteria(new FlagTerm(new Flags(Flags.Flag.SEEN), false), limit);
    }

    public void readAllMessagesFrom(String specificEmail, int limit) {
        readMessagesWithCriteria(new FromAddressTerm(specificEmail), limit);
    }

    public void readAllMessagesTo(String specificEmail, int limit) {
        readMessagesWithCriteria(new ToAddressTerm(specificEmail), limit);
    }

    private void readMessagesWithCriteria(SearchTerm searchTerm, int limit) {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(properties);
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

            int count = 0;
            for (Message message : inboxMessages) {
                if (count >= limit) {
                    break;
                }
                processMessage(message);
                count++;
            }

            for (Message message : sentMessages) {
                if (count >= limit) {
                    break;
                }
                processMessage(message);
                count++;
            }

            inbox.close(false);
            sent.close(false);
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getThreadId(Message message) throws MessagingException {
        String[] references = message.getHeader("References");
        String[] inReplyTo = message.getHeader("In-Reply-To");

        if (references != null && references.length > 0) {
            return extractThreadId(references[0]);
        } else if (inReplyTo != null && inReplyTo.length > 0) {
            return extractThreadId(inReplyTo[0]);
        } else {
            return "Not Available";
        }
    }

    private String extractThreadId(String headerValue) {
        String[] parts = headerValue.split("\\s+");
        if (parts.length > 0) {
            return parts[0];
        }
        return "Not Available";
    }

    private void processMessage(Message message) {
        try {
            String sender = InternetAddress.toString(message.getFrom());
            String subject = message.getSubject();
            String content = getTextFromMessage(message);
            String threadId = getThreadId(message);

            System.out.println("Sender: " + sender);
            System.out.println("Subject: " + subject);
            System.out.println("Content: " + content);
            System.out.println("Thread ID: " + threadId);
            System.out.println("------------------------");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
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

    public void mailSomeGivenId(String to, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static class FromAddressTerm extends SearchTerm {
        private final String specificEmail;

        public FromAddressTerm(String specificEmail) {
            this.specificEmail = specificEmail;
        }

        @Override
        public boolean match(Message message) {
            try {
                Address[] fromAddresses = message.getFrom();
                if (fromAddresses != null) {
                    for (Address fromAddress : fromAddresses) {
                        if (fromAddress.toString().contains(specificEmail)) {
                            return true;
                        }
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private static class ToAddressTerm extends SearchTerm {
        private final String specificEmail;

        public ToAddressTerm(String specificEmail) {
            this.specificEmail = specificEmail;
        }

        @Override
        public boolean match(Message message) {
            try {
                Address[] toAddresses = message.getRecipients(Message.RecipientType.TO);
                if (toAddresses != null) {
                    for (Address toAddress : toAddresses) {
                        if (toAddress.toString().contains(specificEmail)) {
                            return true;
                        }
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static void main(String[] args) {
        GmailClient gmailClient = new GmailClient();

        gmailClient.readAllMessagesTo("mlokesh.mamta@gmail.com", 5);
    }
}