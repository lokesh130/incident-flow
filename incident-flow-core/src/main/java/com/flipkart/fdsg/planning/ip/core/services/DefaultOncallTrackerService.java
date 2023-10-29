package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.OncallTrackerDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.ActiveOncallGroupDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.FollowupDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class DefaultOncallTrackerService implements OncallTrackerService {

    private final OncallTrackerDAO oncallTrackerDAO;
    private final FollowupService followupService;

    @Inject
    public DefaultOncallTrackerService(OncallTrackerDAO oncallTrackerDAO, FollowupService followupService) {
        this.oncallTrackerDAO = oncallTrackerDAO;
        this.followupService = followupService;
    }

    @Override
    @Transactional
    public List<OncallTracker> findAll() {
        return oncallTrackerDAO.findAll();
    }

    @Override
    @Transactional
    public List<OncallTracker> findByPrimaryUserId(Long primaryUserId) {
        return oncallTrackerDAO.findByPrimaryUserId(primaryUserId);
    }

    @Override
    @Transactional
    public List<OncallTracker> findBySecondaryUserId(Long secondaryUserId) {
        return oncallTrackerDAO.findBySecondaryUserId(secondaryUserId);
    }

    @Override
    @Transactional
    public List<OncallTracker> findByPrimaryAndSecondaryUserId(Long primaryUserId, Long secondaryUserId) {
        return oncallTrackerDAO.findByPrimaryAndSecondaryUserId(primaryUserId, secondaryUserId);
    }

    @Override
    @Transactional
    public List<OncallTracker> findByOncallGroupId(Long oncallGroupId) {
        return oncallTrackerDAO.findByOncallGroupId(oncallGroupId);
    }

    @Override
    public OncallTracker findByThreadId(String threadId) {
        return oncallTrackerDAO.findByThreadId(threadId);
    }

    @Override
    @Transactional
    public void updateOncallTracker(String threadId, OncallTrackerDTO dto) {
        OncallTracker tracker = oncallTrackerDAO.findByThreadId(threadId);

        if (tracker == null) {
            throw new IllegalArgumentException("No OncallTracker found with threadId: " + threadId);
        }

        updateNonNullFields(dto, tracker);
    }

    private void updateNonNullFields(OncallTrackerDTO dto, OncallTracker tracker) {
        if (dto.getTitle() != null) {
            tracker.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            tracker.setDescription(dto.getDescription());
        }

        if (dto.getActiveOncallGroup() != null) {
            // Assuming you have a mapping method in ActiveOncallGroupDTO
            tracker.setActiveOncallGroup(ActiveOncallGroupDTO.mapToEntity(dto.getActiveOncallGroup()));
        }

        if (dto.getOncallStatus() != null) {
            tracker.setOncallStatus(dto.getOncallStatus());
        }

        if (dto.getStatus() != null) {
            tracker.setStatus(dto.getStatus());
        }

        if (dto.getPriority() != null) {
            tracker.setPriority(dto.getPriority());
        }

        if (dto.getRcaDoc() != null) {
            tracker.setRcaDoc(dto.getRcaDoc());
        }

        if (dto.getSummary() != null) {
            tracker.setSummary(dto.getSummary());
        }
    }

    @Override
    @Transactional
    public OncallTracker addOncallTracker(OncallTrackerDTO dto) {
        return oncallTrackerDAO.add(dto);
    }


    @Override
    public List<OncallTracker> findAllByMatchingSubject(String subject) {
        log.info("Finding all OncallTrackers by matching subject: {}", subject);

        String cleanedSubject = cleanSubject(subject.toLowerCase());
        log.debug("Cleaned subject: {}", cleanedSubject);

        List<OncallTracker> allOncallTrackers = oncallTrackerDAO.findAll();
        log.debug("Total OncallTrackers found: {}", allOncallTrackers.size());

        List<OncallTracker> matchingTrackers = findAllMatchingTrackers(cleanedSubject, allOncallTrackers);

        if (!matchingTrackers.isEmpty()) {
            log.info("Matching trackers found for subject: {}", subject);
            return matchingTrackers;
        } else {
            log.info("No match found for subject: {}", subject);
            return Collections.emptyList();
        }
    }


    private String cleanSubject(String subject) {
        // Remove special characters from the subject
        return subject.replaceAll("[^a-zA-Z0-9]", "");
    }

    private List<OncallTracker> findAllMatchingTrackers(String cleanedSubject, List<OncallTracker> oncallTrackers) {
        log.info("Finding all matching trackers for cleaned subject: {}", cleanedSubject);

        List<OncallTracker> matchingTrackers = new ArrayList<>();

        for (OncallTracker tracker : oncallTrackers) {
            String trackerTitle = cleanSubject(tracker.getTitle().toLowerCase());
            log.info("Cleaned title of tracker {}: {}", tracker.getId(), trackerTitle);

            if (trackerTitle.equals(cleanedSubject)) {
                log.info("Matching tracker found for cleaned subject: {}", cleanedSubject);
                matchingTrackers.add(tracker);
            }
        }

        if (matchingTrackers.isEmpty()) {
            log.info("No matching trackers found for cleaned subject: {}", cleanedSubject);
        }

        return matchingTrackers;
    }

    @Override
    @Transactional
    public List<OncallTracker> findActiveOncallTrackersWithTimeDiff(Long timeDuration) {
        log.info("Finding active OncallTrackers with time difference...");
        LocalDateTime currentTime = LocalDateTime.now();
        Duration threshold = Duration.ofSeconds(timeDuration);

        List<OncallTracker> activeOncallTrackers = oncallTrackerDAO.findActiveOncallTrackers();
        log.debug("Total active OncallTrackers found: {}", activeOncallTrackers.size());

        List<OncallTracker> filteredTrackers = activeOncallTrackers.stream()
                .filter(tracker -> {
                    Duration timeDiff = Duration.between(tracker.getUpdatedAt(), currentTime);
                    boolean isExpired = timeDiff.compareTo(threshold) > 0;
                    if (isExpired) {
                        log.info("OncallTracker {} is considered expired with a time difference of {} seconds.",
                                tracker.getId(), timeDiff.getSeconds());
                    }
                    return isExpired;
                })
                .collect(Collectors.toList());

        log.info("Found {} active OncallTrackers with time difference greater than {} seconds.", filteredTrackers.size(), threshold.getSeconds());
        return filteredTrackers;
    }

    @Override
    @Transactional
    public List<FollowupDTO> generateFollowups(Long timeDuration) {
        log.info("Generating followups for active OncallTrackers...");
        List<OncallTracker> oncallTrackers = findActiveOncallTrackersWithTimeDiff(timeDuration);

        List<FollowupDTO> followupDTOs = oncallTrackers.stream()
                .map(tracker -> {
                    String title = tracker.getTitle();
                    Duration timeDiff = Duration.between(tracker.getUpdatedAt(), LocalDateTime.now());
                    String message = formatTimeDifference(timeDiff) + " since last reply";

                    if (followupService.findByOncallTrackerId(tracker.getId()).isEmpty()) {
                        FollowupDTO followupDTO = new FollowupDTO();
                        followupDTO.setOncallTitle(title);
                        followupDTO.setMessage(message);
                        followupService.addFollowups(tracker, Collections.singletonList(followupDTO));
                        log.info("Followup generated for OncallTracker {} with title: '{}'", tracker.getId(), title);
                        return followupDTO;
                    } else {
                        log.info("Followups already exist for OncallTracker {} with title: '{}'. Skipping generation.", tracker.getId(), title);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        log.info("Generated {} followups for active OncallTrackers.", followupDTOs.size());
        return followupDTOs;
    }

    private String formatTimeDifference(Duration timeDiff) {
        long seconds = timeDiff.getSeconds();
        if (seconds < 60) {
            return seconds + " seconds";
        }

        long minutes = timeDiff.toMinutes();
        if (minutes < 60) {
            return minutes + " minutes";
        }

        long hours = timeDiff.toHours();
        if (hours < 24) {
            return hours + " hours";
        }

        long days = timeDiff.toDays();
        if (days < 30) {
            return days + " days";
        }

        long months = days / 30;
        return months + " months";
    }
}
