package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.clients.GmailClient;
import com.flipkart.fdsg.planning.ip.core.dtos.MessageDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
import com.flipkart.fdsg.planning.ip.core.entities.WatchEmail;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DefaultRefreshService implements RefreshService {

    private final WatchEmailService watchEmailService;
    private final RefreshTimestampLogService refreshTimestampLogService;
    private final GmailClient gmailClient;
    private final RawEmailService rawEmailService;

    @Inject
    public DefaultRefreshService(WatchEmailService watchEmailService,
                                 RefreshTimestampLogService refreshTimestampLogService,
                                 GmailClient gmailClient,
                                 RawEmailService rawEmailService) {
        this.watchEmailService = watchEmailService;
        this.refreshTimestampLogService = refreshTimestampLogService;
        this.gmailClient = gmailClient;
        this.rawEmailService = rawEmailService;
    }

    @Override
    @Transactional
    public List<RawEmailDTO> refreshEmails() {
        log.info("Syncing emails...");

        // Get watch emails
        List<String> watchEmails = watchEmailService.getAllEmails().stream()
                .map(WatchEmail::getEmail)
                .collect(Collectors.toList());
        log.info("Watch emails: {}", watchEmails);

        // Get the latest refresh timestamp
        Long latestRefreshTimestamp = refreshTimestampLogService.getLatestRefreshTimestamp();
        log.info("Latest refresh timestamp: {}", latestRefreshTimestamp);

        // Call GmailClient to get messages
        List<MessageDTO> messages = gmailClient.getMessages(watchEmails, latestRefreshTimestamp);
        log.info("Retrieved {} messages from Gmail.", messages.size());

        // Group messages by thread ID
        Map<String, List<MessageDTO>> messagesByThreadId = messages.stream()
                .collect(Collectors.groupingBy(MessageDTO::getThreadId));

        // Assign numeric IDs based on the order of messages in each thread
        int messageOrder = 1;
        List<RawEmailDTO> rawEmailDTOs = new ArrayList<>();

        for (List<MessageDTO> threadMessages : messagesByThreadId.values()) {
            threadMessages.sort(Comparator.comparingLong(message -> Long.parseLong(message.getMessageId())));

            for (MessageDTO messageDTO : threadMessages) {
                RawEmailDTO rawEmailDTO = RawEmailDTO.builder()
                        .subject(messageDTO.getSubject())
                        .from(messageDTO.getFrom())
                        .toList(messageDTO.getToList())
                        .body(messageDTO.getContent())
                        .messageId(messageDTO.getMessageId())
                        .threadId(messageDTO.getThreadId())
                        .messageOrder(messageOrder++)
                        .build();
                rawEmailDTOs.add(rawEmailDTO);
            }
        }

        log.info("Converted {} messages to RawEmailDTOs.", rawEmailDTOs.size());

        // Add RawEmails
        rawEmailService.addRawEmails(rawEmailDTOs);
        log.info("Added {} RawEmails to the database.", rawEmailDTOs.size());

        log.info("Email synchronization completed.");
        return rawEmailDTOs;
    }
}
