package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.FollowupCriteriaDAO;
import com.flipkart.fdsg.planning.ip.core.entities.FollowupCriteria;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class DefaultFollowupCriteriaService implements FollowupCriteriaService {

    private final FollowupCriteriaDAO followupCriteriaDAO;

    @Inject
    public DefaultFollowupCriteriaService(FollowupCriteriaDAO followupCriteriaDAO) {
        this.followupCriteriaDAO = followupCriteriaDAO;
    }

    @Override
    public List<FollowupCriteria> findAll() {
        return followupCriteriaDAO.findAll();
    }

    @Override
    public Optional<FollowupCriteria> findLatestFollowupCriteria() {
        return followupCriteriaDAO.findLatestFollowupCriteria();
    }

    @Override
    public void addFollowupCriteria(FollowupCriteria followupCriteria) {
        followupCriteriaDAO.addFollowupCriteria(followupCriteria);
    }

    @Override
    public void updateExpectedDurationOfLatestEntry(Long newExpectedDuration) {
        followupCriteriaDAO.updateExpectedDurationOfLatestEntry(newExpectedDuration);
    }
}
