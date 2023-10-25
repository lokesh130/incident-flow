package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.FollowupDAO;
import com.flipkart.fdsg.planning.ip.core.entities.Followup;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultFollowupService implements FollowupService {
    private final FollowupDAO followupDAO;

    @Inject
    public DefaultFollowupService(FollowupDAO followupDAO) {
        this.followupDAO = followupDAO;
    }

    @Override
    @Transactional
    public List<Followup> findAll() {
        return followupDAO.findAll();
    }

    @Override
    @Transactional
    public Followup findById(int followupId) {
        return followupDAO.findById(followupId);
    }
}
