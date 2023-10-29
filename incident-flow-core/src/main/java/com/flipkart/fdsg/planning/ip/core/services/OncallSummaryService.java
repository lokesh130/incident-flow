package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.OncallSummaryDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallSummary;

import java.util.List;

public interface OncallSummaryService {
    List<OncallSummary> findAll();

    List<OncallSummary> findByOncallTrackerId(Long oncallTrackerId);

    void add(List<OncallSummaryDTO> oncallSummaryDTOS);
}
