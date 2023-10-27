package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Slf4j

public class DefaultSyncService implements SyncService {

    private final RefreshService refreshService;
    private final OncallTrackerService oncallTrackerService;
    private final RawEmailService rawEmailService;

    private final RefreshTimestampLogService refreshTimestampLogService;

    @Inject
    public DefaultSyncService(RefreshService refreshService, OncallTrackerService oncallTrackerService, RawEmailService rawEmailService, RefreshTimestampLogService refreshTimestampLogService) {
        this.refreshService = refreshService;
        this.oncallTrackerService = oncallTrackerService;
        this.rawEmailService = rawEmailService;
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
                oncallTrackerService.addOncallTracker(updatedOncallTracker);
            } else {
                log.info("Updating OncallTracker for threadId: {}", threadId);
                oncallTrackerService.updateOncallTracker(threadId, updatedOncallTracker);
            }
        } else {
            log.info("No updated OncallTracker found for threadId: {}", threadId);
        }
    }
}
