package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.HistoricalUserDAO;
import com.flipkart.fdsg.planning.ip.core.entities.HistoricalUser;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultHistoricalUserService implements HistoricalUserService {

    private final HistoricalUserDAO historicalUserDAO;

    @Inject
    public DefaultHistoricalUserService(HistoricalUserDAO historicalUserDAO) {
        this.historicalUserDAO = historicalUserDAO;
    }

    @Override
    @Transactional
    public List<HistoricalUser> findAll() {
        return historicalUserDAO.findAll();
    }

    @Override
    @Transactional
    public List<HistoricalUser> findByOncallTrackerId(Long oncallTrackerId) {
        return historicalUserDAO.findByOncallTrackerId(oncallTrackerId);
    }
}
