package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.FollowupDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.FollowupDTO;
import com.flipkart.fdsg.planning.ip.core.entities.Followup;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
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

    @Override
    @Transactional
    public void addFollowups(OncallTracker oncallTracker,  List<FollowupDTO> followupDTOs) {
        for (FollowupDTO followupDTO : followupDTOs) {
            Followup followup = new Followup();
            followup.setMessage(followupDTO.getMessage());
            followup.setPriority(followupDTO.getPriority());
            followup.setOncallTracker(oncallTracker);
            followupDAO.save(followup);
            log.info("Followup added for OncallTracker with title: {}", followupDTO.getOncallTitle());

        }
    }

    @Override
    @Transactional
    public List<Followup> findByOncallTrackerId(Long oncallTrackerId) {
        return followupDAO.findByOncallTrackerId(oncallTrackerId);
    }
}
