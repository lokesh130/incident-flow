package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.FollowupDTO;
import com.flipkart.fdsg.planning.ip.core.entities.Followup;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;

import java.util.List;

public interface FollowupService {
    List<Followup> findAll();

    Followup findById(int followupId);

    void addFollowups(OncallTracker oncallTracker, List<FollowupDTO> followupDTOs);

    List<Followup> findByOncallTrackerId(Long oncallTrackerId);
}
