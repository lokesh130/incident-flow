package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;

import java.util.List;

public interface OncallTrackerService {
    List<OncallTracker> findAll();

    List<OncallTracker> findByPrimaryUserId(Long primaryUserId);

    List<OncallTracker> findBySecondaryUserId(Long secondaryUserId);

    List<OncallTracker> findByPrimaryAndSecondaryUserId(Long primaryUserId, Long secondaryUserId);

    List<OncallTracker> findByOncallGroupId(Long oncallGroupId);

    OncallTracker findByThreadId(String threadId);

    void updateOncallTracker(String threadId, OncallTrackerDTO dto);

    OncallTracker addOncallTracker(OncallTrackerDTO dto);

    List<OncallTracker> findAllByMatchingSubject(String subject);
}
