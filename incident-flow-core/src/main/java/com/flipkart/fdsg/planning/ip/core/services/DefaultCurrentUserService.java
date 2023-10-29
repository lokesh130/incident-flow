package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.CurrentUserDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.CurrentUserDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
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

    @Override
    @Transactional
    public void add(List<CurrentUserDTO> currentUserDTOs) {
        currentUserDTOs.stream().forEach(currentUserDTO -> {
            CurrentUser currentUser = CurrentUser.builder()
                    .userId(currentUserDTO.getUserId())
                    .oncallTracker(OncallTrackerDTO.mapToEntity(currentUserDTO.getOncallTracker()))
                    .build();
            currentUserDAO.add(currentUser);
        });
    }
}
