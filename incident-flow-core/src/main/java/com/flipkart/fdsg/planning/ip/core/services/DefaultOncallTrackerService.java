package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.OncallTrackerDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.ActiveOncallGroupDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DefaultOncallTrackerService implements OncallTrackerService {

    private final OncallTrackerDAO oncallTrackerDAO;

    @Inject
    public DefaultOncallTrackerService(OncallTrackerDAO oncallTrackerDAO) {
        this.oncallTrackerDAO = oncallTrackerDAO;
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

        OncallTracker bestMatch = findBestMatchingTracker(cleanedSubject, allOncallTrackers);

        if (bestMatch != null) {
            log.info("Best match found for subject: {}", subject);
            return Collections.singletonList(bestMatch);
        } else {
            log.info("No match found for subject: {}", subject);
            return Collections.emptyList();
        }
    }


    private String cleanSubject(String subject) {
        // Remove special characters from the subject
        return subject.replaceAll("[^a-zA-Z0-9]", "");
    }

    private OncallTracker findBestMatchingTracker(String cleanedSubject, List<OncallTracker> oncallTrackers) {
        log.info("Finding best matching tracker for cleaned subject: {}", cleanedSubject);

        OncallTracker bestMatch = null;

        for (OncallTracker tracker : oncallTrackers) {
            String trackerTitle = cleanSubject(tracker.getTitle().toLowerCase());
            log.debug("Cleaned title of tracker {}: {}", tracker.getId(), trackerTitle);

            if (trackerTitle.equals(cleanedSubject)) {
                log.info("Best match found for cleaned subject: {}", cleanedSubject);
                bestMatch = tracker;
                break;
            }
        }

        if (bestMatch == null) {
            log.info("No best match found for cleaned subject: {}", cleanedSubject);
        }

        return bestMatch;
    }
}
