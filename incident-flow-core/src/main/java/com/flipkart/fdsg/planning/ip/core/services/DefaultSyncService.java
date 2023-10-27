package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.OncallSuggestionDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j

public class DefaultSyncService implements SyncService {

    private final RefreshService refreshService;
    private final OncallTrackerService oncallTrackerService;
    private final RawEmailService rawEmailService;

    private final OncallSuggestionService oncallSuggestionService;

    private final RefreshTimestampLogService refreshTimestampLogService;

    @Inject
    public DefaultSyncService(RefreshService refreshService, OncallTrackerService oncallTrackerService, RawEmailService rawEmailService, OncallSuggestionService oncallSuggestionService, RefreshTimestampLogService refreshTimestampLogService) {
        this.refreshService = refreshService;
        this.oncallTrackerService = oncallTrackerService;
        this.rawEmailService = rawEmailService;
        this.oncallSuggestionService = oncallSuggestionService;
        this.refreshTimestampLogService = refreshTimestampLogService;
    }

    @Override
    public void synchronizeEmails() {
        log.info("Synchronizing emails...");

        List<RawEmailDTO> rawEmailDTOList = refreshService.refreshEmails();
        log.info("Fetched RawEmailDTOs: {}", rawEmailDTOList);

        for (RawEmailDTO rawEmailDTO : rawEmailDTOList) {
            synchronizeEmail(rawEmailDTO);
        }

        String lastMessageId = rawEmailDTOList.isEmpty() ? null : rawEmailDTOList.get(rawEmailDTOList.size() - 1).getMessageId();
        if(!Objects.isNull(lastMessageId)) {
            refreshTimestampLogService.addRefreshTimestamp(Long.parseLong(lastMessageId));
        }
        log.info("Email synchronization complete.");
    }

    private void synchronizeEmail(RawEmailDTO rawEmailDTO) {
        String threadId = rawEmailDTO.getThreadId();
        log.info("Processing email with threadId: {}", threadId);

        OncallTrackerDTO currentOncallTracker = OncallTrackerDTO.map(oncallTrackerService.findByThreadId(threadId));
        OncallTrackerDTO updatedOncallTracker = rawEmailService.getUpdatedOncallTracker(currentOncallTracker, rawEmailDTO);

        if (updatedOncallTracker != null) {
            if (currentOncallTracker == null) {
                log.info("Adding OncallTracker for threadId: {}", threadId);
                OncallTracker oncallTracker = oncallTrackerService.addOncallTracker(updatedOncallTracker);

                log.info("Finding suggestions for subject: {}", rawEmailDTO.getSubject());
                List<OncallSuggestionDTO> oncallSuggestionDTOS = oncallTrackerService.findAllByMatchingSubject(rawEmailDTO.getSubject())
                        .stream()
                        .filter(oncallTracker1 -> oncallTracker1.getStatus().equals("CLOSED"))
                        .map(oncallTracker1 -> OncallSuggestionDTO.builder()
                                .suggestion(oncallTracker1.getSummary())
                                .oncallTracker(OncallTrackerDTO.map(oncallTracker))
                                .build())
                        .collect(Collectors.toList());

                log.info("Adding {} suggestions", oncallSuggestionDTOS.size());
                log.info("Suggestions: {}", oncallSuggestionDTOS);
                oncallSuggestionService.add(oncallSuggestionDTOS);
            } else {
                log.info("Updating OncallTracker for threadId: {}", threadId);
                oncallTrackerService.updateOncallTracker(threadId, updatedOncallTracker);
            }
        } else {
            log.info("No updated OncallTracker found for threadId: {}", threadId);
        }
    }
}
