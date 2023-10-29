package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.HistoricalUserDTO;
import com.flipkart.fdsg.planning.ip.core.entities.HistoricalUser;

import java.util.List;

public interface HistoricalUserService {
    List<HistoricalUser> findAll();

    List<HistoricalUser> findByOncallTrackerId(Long oncallTrackerId);

    void add(List<HistoricalUserDTO> historicalUserDTOs);
}
