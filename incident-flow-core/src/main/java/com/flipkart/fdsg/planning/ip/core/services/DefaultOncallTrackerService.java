package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.OncallTrackerDAO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

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
}
