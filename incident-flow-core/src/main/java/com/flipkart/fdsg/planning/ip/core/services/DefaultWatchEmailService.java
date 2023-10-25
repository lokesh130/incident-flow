package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.WatchEmailDAO;
import com.flipkart.fdsg.planning.ip.core.entities.WatchEmail;
import com.flipkart.fdsg.planning.ip.core.exceptions.RecordNotFoundException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultWatchEmailService implements WatchEmailService {

    private final WatchEmailDAO watchEmailDAO;

    @Inject
    public DefaultWatchEmailService(WatchEmailDAO watchEmailDAO) {
        this.watchEmailDAO = watchEmailDAO;
    }

    @Override
    public List<WatchEmail> getAllEmails() {
        return watchEmailDAO.findAll();
    }

    @Override
    public void addEmail(String email) {
        watchEmailDAO.addEmail(email);
    }

    @Transactional
    public void deleteEmail(String email) {
        WatchEmail watchEmail = watchEmailDAO.findByEmail(email);
        if (watchEmail == null) {
            throw new RecordNotFoundException(WatchEmail.class, "Email not found: " + email);
        }
        watchEmailDAO.deleteEmail(email);
    }
}
