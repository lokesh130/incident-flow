package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;

import java.util.List;

public interface OncallTrackerService {
    List<OncallTracker> findAll();

    List<OncallTracker> findByPrimaryUserId(Long primaryUserId);

    List<OncallTracker> findBySecondaryUserId(Long secondaryUserId);

    List<OncallTracker> findByPrimaryAndSecondaryUserId(Long primaryUserId, Long secondaryUserId);

    List<OncallTracker> findByOncallGroupId(Long oncallGroupId);
}
