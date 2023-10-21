package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.OncallSuggestion;

import java.util.List;

public interface OncallSuggestionService {
    List<OncallSuggestion> findAll();

    List<OncallSuggestion> findByOncallTrackerId(Long oncallTrackerId);
}
