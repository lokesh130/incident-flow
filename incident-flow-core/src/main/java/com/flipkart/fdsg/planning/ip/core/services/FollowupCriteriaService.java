package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.FollowupCriteria;

import java.util.List;
import java.util.Optional;

public interface FollowupCriteriaService {

    List<FollowupCriteria> findAll();

    Optional<FollowupCriteria> findLatestFollowupCriteria();

    void addFollowupCriteria(FollowupCriteria followupCriteria);

    void updateExpectedDurationOfLatestEntry(Long newExpectedDuration);
}
