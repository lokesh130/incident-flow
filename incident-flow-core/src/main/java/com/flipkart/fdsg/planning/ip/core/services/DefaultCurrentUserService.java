package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.CurrentUserDAO;
import com.flipkart.fdsg.planning.ip.core.entities.CurrentUser;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultCurrentUserService implements CurrentUserService {

    private final CurrentUserDAO currentUserDAO;

    @Inject
    public DefaultCurrentUserService(CurrentUserDAO currentUserDAO) {
        this.currentUserDAO = currentUserDAO;
    }

    @Override
    @Transactional
    public List<CurrentUser> findAll() {
        return currentUserDAO.findAll();
    }

    @Override
    @Transactional
    public List<CurrentUser> findByOncallTrackerId(Long oncallTrackerId) {
        return currentUserDAO.findByOncallTrackerId(oncallTrackerId);
    }
}
