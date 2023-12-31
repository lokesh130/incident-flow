package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.ActiveOncallGroup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActiveOncallGroupService {
    List<ActiveOncallGroup> findAll();

    Optional<ActiveOncallGroup> findLatestActiveGroup();

    Optional<ActiveOncallGroup> findActiveGroupByDate(LocalDateTime givenDate);
}
