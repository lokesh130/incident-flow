package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.AlertDAO;
import com.flipkart.fdsg.planning.ip.core.entities.Alert;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultAlertService implements AlertService {
    private final AlertDAO alertDAO;

    @Inject
    public DefaultAlertService(AlertDAO alertDAO) {
        this.alertDAO = alertDAO;
    }

    @Override
    @Transactional
    public List<Alert> findAll() {
        return alertDAO.findAll();
    }

    @Override
    @Transactional
    public Alert findById(int alertId) {
        return alertDAO.findById(alertId);
    }
}
