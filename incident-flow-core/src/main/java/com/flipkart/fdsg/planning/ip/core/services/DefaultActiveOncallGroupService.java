package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.ActiveOncallGroupDAO;
import com.flipkart.fdsg.planning.ip.core.entities.ActiveOncallGroup;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DefaultActiveOncallGroupService implements ActiveOncallGroupService {

    private final ActiveOncallGroupDAO activeOncallGroupDAO;

    @Inject
    public DefaultActiveOncallGroupService(ActiveOncallGroupDAO activeOncallGroupDAO) {
        this.activeOncallGroupDAO = activeOncallGroupDAO;
    }

    @Override
    @Transactional
    public List<ActiveOncallGroup> findAll() {
        return activeOncallGroupDAO.findAll();
    }

    @Override
    @Transactional
    public Optional<ActiveOncallGroup> findLatestActiveGroup() {
        return activeOncallGroupDAO.findLatestActiveGroup();
    }

    @Override
    @Transactional
    public Optional<ActiveOncallGroup> findActiveGroupByDate(LocalDateTime givenDate) {
        return activeOncallGroupDAO.findActiveGroupBySingleDate(givenDate);
    }
}
