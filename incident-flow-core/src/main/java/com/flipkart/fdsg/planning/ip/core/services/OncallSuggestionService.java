package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.OncallSuggestionDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallSuggestion;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;

import java.util.List;

public interface OncallSuggestionService {
    List<OncallSuggestion> findAll();

    List<OncallSuggestion> findByOncallTrackerId(Long oncallTrackerId);

    void add(List<OncallSuggestionDTO> oncallSuggestionDTOs);
}
