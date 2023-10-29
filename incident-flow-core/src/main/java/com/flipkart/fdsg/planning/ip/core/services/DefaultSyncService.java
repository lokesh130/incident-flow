package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.*;
import com.flipkart.fdsg.planning.ip.core.entities.CurrentUser;
import com.flipkart.fdsg.planning.ip.core.entities.FollowupCriteria;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import com.flipkart.fdsg.planning.ip.core.exceptions.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j

public class DefaultSyncService implements SyncService {

    private final RefreshService refreshService;
    private final OncallTrackerService oncallTrackerService;
    private final RawEmailService rawEmailService;

    private final OncallSummaryService oncallSummaryService;

    private final RefreshTimestampLogService refreshTimestampLogService;

    private final CurrentUserService currentUserService;

    private final HistoricalUserService historicalUserService;

    private final FollowupCriteriaService followupCriteriaService;

    @Inject
    public DefaultSyncService(RefreshService refreshService, OncallTrackerService oncallTrackerService, RawEmailService rawEmailService, OncallSummaryService oncallSummaryService, RefreshTimestampLogService refreshTimestampLogService, CurrentUserService currentUserService, HistoricalUserService historicalUserService, FollowupCriteriaService followupCriteriaService) {
        this.refreshService = refreshService;
        this.oncallTrackerService = oncallTrackerService;
        this.rawEmailService = rawEmailService;
        this.oncallSummaryService = oncallSummaryService;
        this.refreshTimestampLogService = refreshTimestampLogService;
        this.currentUserService = currentUserService;
        this.historicalUserService = historicalUserService;
        this.followupCriteriaService = followupCriteriaService;
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

    @Override
    public void synchronizeFollowups() {
        Long expectedDuration = followupCriteriaService.findLatestFollowupCriteria().get().getExpectedDuration();
        oncallTrackerService.generateFollowups(expectedDuration);
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
                List<OncallTracker> similarOncallTrackers = oncallTrackerService.findAllByMatchingSubject(rawEmailDTO.getSubject())
                        .stream()
                        .filter(oncallTracker1 -> oncallTracker1.getStatus().equals("CLOSED")).collect(Collectors.toList());

                log.info("Similar Oncall Trackers Size: {}", similarOncallTrackers.size());

                List<OncallSummaryDTO> oncallSummaryDTOS = similarOncallTrackers.stream()
                        .map(oncallTracker1 -> OncallSummaryDTO.builder()
                                .suggestion(oncallTracker1.getSummary())
                                .oncallTracker(OncallTrackerDTO.map(oncallTracker))
                                .threadDate(oncallTracker1.getCreatedAt().toString())
                                .build())
                        .collect(Collectors.toList());

                List<HistoricalUserDTO> historicalUserDTOS = getHistoricalUserDTOList(similarOncallTrackers, oncallTracker);
                List<CurrentUserDTO> currentUserDTOS = getCurrentUserDTOList(rawEmailDTO, oncallTracker);

                log.info("Adding {} suggestions", oncallSummaryDTOS.size());
                log.info("Suggestions: {}", oncallSummaryDTOS);
                oncallSummaryService.add(oncallSummaryDTOS);
                historicalUserService.add(historicalUserDTOS);
                currentUserService.add(currentUserDTOS);
            } else {
                log.info("Updating OncallTracker for threadId: {}", threadId);
                OncallTracker oncallTracker = oncallTrackerService.findByThreadId(threadId);
                List<CurrentUserDTO> currentUserDTOS = getCurrentUserDTOList(rawEmailDTO, oncallTracker);

                oncallTrackerService.updateOncallTracker(threadId, updatedOncallTracker);
                currentUserService.add(currentUserDTOS);
            }
        } else {
            log.info("No updated OncallTracker found for threadId: {}", threadId);
        }
    }

    private List<CurrentUserDTO> getCurrentUserDTOList(RawEmailDTO rawEmailDTO, OncallTracker oncallTracker) {
        log.info("Getting CurrentUserDTO list for OncallTracker with ID: {}", oncallTracker.getId());

        List<String> currentUserIds = currentUserService.findByOncallTrackerId(oncallTracker.getId())
                .stream()
                .peek(currentUser -> log.info("Found CurrentUser with ID: {}", currentUser.getUserId()))
                .map(CurrentUser::getUserId)
                .collect(Collectors.toList());

        log.info("Current user IDs: {}", currentUserIds);

        List<String> newUsersIds = Stream.concat(
                        rawEmailDTO.getToList().stream(),
                        Stream.of(rawEmailDTO.getFrom())
                )
                .peek(userId -> log.info("Found new user ID: {}", userId))
                .collect(Collectors.toList());

        log.info("New user IDs: {}", newUsersIds);

        List<String> newUsersNotInCurrent = newUsersIds.stream()
                .filter(userId -> !currentUserIds.contains(userId))
                .peek(userId -> log.info("New user ID not in current users: {}", userId))
                .collect(Collectors.toList());

        log.info("New user IDs not in current users: {}", newUsersNotInCurrent);

        List<CurrentUserDTO> currentUserDTOList = newUsersNotInCurrent.stream()
                .map(userId -> CurrentUserDTO.builder()
                        .userId(userId)
                        .oncallTracker(OncallTrackerDTO.map(oncallTracker))
                        .build())
                .peek(currentUserDTO -> log.debug("Created CurrentUserDTO: {}", currentUserDTO))
                .collect(Collectors.toList());

        log.info("Finished getting CurrentUserDTO list. Total Count: {}", currentUserDTOList.size());
        return currentUserDTOList;
    }

    private List<HistoricalUserDTO> getHistoricalUserDTOList(List<OncallTracker> similarOncallTrackers, OncallTracker oncallTracker) {
        log.info("Getting HistoricalUserDTO list for OncallTracker with ID: {}", oncallTracker.getId());

        List<HistoricalUserDTO> historicalUserDTOList = similarOncallTrackers.stream()
                .flatMap(oncallTracker1 -> {
                    List<String> userIds = rawEmailService.findByThreadId(oncallTracker1.getThreadId())
                            .stream()
                            .peek(rawEmail -> log.debug("Found RawEmail with thread ID: {}", rawEmail.getThreadId()))
                            .flatMap(rawEmail -> Stream.concat(rawEmail.getToList().stream(), Stream.of(rawEmail.getFrom())))
                            .distinct()
                            .peek(userId -> log.debug("Found user ID: {}", userId))
                            .collect(Collectors.toList());

                    log.debug("User IDs from RawEmails: {}", userIds);

                    return userIds.stream()
                            .map(userId -> HistoricalUserDTO.builder()
                                    .oncallTracker(OncallTrackerDTO.map(oncallTracker))
                                    .userId(userId)
                                    .build())
                            .peek(historicalUserDTO -> log.debug("Created HistoricalUserDTO: {}", historicalUserDTO));
                })
                .distinct()
                .peek(historicalUserDTO -> log.debug("Distinct HistoricalUserDTO: {}", historicalUserDTO))
                .collect(Collectors.toList());

        log.info("Finished getting HistoricalUserDTO list. Total Count: {}", historicalUserDTOList.size());
        return historicalUserDTOList;
    }
}
