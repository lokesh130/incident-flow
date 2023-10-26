package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.RefreshTimestampLogDAO;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class DefaultRefreshTimestampLogService implements RefreshTimestampLogService {

    private final RefreshTimestampLogDAO refreshTimestampLogDAO;

    @Inject
    public DefaultRefreshTimestampLogService(RefreshTimestampLogDAO refreshTimestampLogDAO) {
        this.refreshTimestampLogDAO = refreshTimestampLogDAO;
    }

    @Override
    @Transactional
    public void addRefreshTimestamp(Long refreshTimestamp) {
        refreshTimestampLogDAO.addRefreshTimestamp(refreshTimestamp);
    }

    @Override
    @Transactional
    public Long getLatestRefreshTimestamp() {
        return refreshTimestampLogDAO.getLatestRefreshTimestamp();
    }
}
