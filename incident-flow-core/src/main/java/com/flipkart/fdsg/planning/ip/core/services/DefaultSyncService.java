package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.clients.GmailClient;
import com.flipkart.fdsg.planning.ip.core.dtos.MessageDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
import com.flipkart.fdsg.planning.ip.core.entities.WatchEmail;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultSyncService implements SyncService {

    private final WatchEmailService watchEmailService;
    private final RefreshTimestampLogService refreshTimestampLogService;
    private final GmailClient gmailClient;
    private final RawEmailService rawEmailService;

    @Inject
    public DefaultSyncService(WatchEmailService watchEmailService,
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
    public void syncEmails() {
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

        // Convert MessageDTO to RawEmailDTO
        List<RawEmailDTO> rawEmailDTOs = messages.stream()
                .map(messageDTO -> RawEmailDTO.builder()
                        .subject(messageDTO.getSubject())
                        .from(messageDTO.getFrom())
                        .toList(messageDTO.getToList())
                        .body(messageDTO.getContent())
                        .messageId(messageDTO.getMessageId())
                        .threadId(messageDTO.getThreadId())
                        .build())
                .collect(Collectors.toList());
        log.info("Converted {} messages to RawEmailDTOs.", rawEmailDTOs.size());

        // Add RawEmails
        rawEmailService.addRawEmails(rawEmailDTOs);
        log.info("Added {} RawEmails to the database.", rawEmailDTOs.size());

        log.info("Email synchronization completed.");
    }
}
