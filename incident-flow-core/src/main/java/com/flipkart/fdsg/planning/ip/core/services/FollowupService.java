package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.Followup;

import java.util.List;

public interface FollowupService {
    List<Followup> findAll();

    Followup findById(int followupId);
}
