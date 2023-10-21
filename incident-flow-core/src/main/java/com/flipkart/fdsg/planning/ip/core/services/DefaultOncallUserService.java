package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.OncallUserDAO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallUser;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class DefaultOncallUserService implements OncallUserService {

    private final OncallUserDAO oncallUserDAO;

    @Inject
    public DefaultOncallUserService(OncallUserDAO oncallUserDAO) {
        this.oncallUserDAO = oncallUserDAO;
    }

    @Override
    @Transactional
    public List<OncallUser> findAll() {
        return oncallUserDAO.findAll();
    }

    @Override
    @Transactional
    public Optional<String> findNextUserIdById(Long currentId) {
        return oncallUserDAO.findNextUserIdById(currentId);
    }
}
