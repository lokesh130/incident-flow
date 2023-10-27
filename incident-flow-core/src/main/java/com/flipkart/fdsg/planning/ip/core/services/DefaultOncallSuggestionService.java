package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.OncallSuggestionDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallSuggestionDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallSuggestion;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultOncallSuggestionService implements OncallSuggestionService {

    private final OncallSuggestionDAO oncallSuggestionDAO;

    @Inject
    public DefaultOncallSuggestionService(OncallSuggestionDAO oncallSuggestionDAO) {
        this.oncallSuggestionDAO = oncallSuggestionDAO;
    }

    @Override
    @Transactional
    public List<OncallSuggestion> findAll() {
        return oncallSuggestionDAO.findAll();
    }

    @Override
    @Transactional
    public List<OncallSuggestion> findByOncallTrackerId(Long oncallTrackerId) {
        return oncallSuggestionDAO.findByOncallTrackerId(oncallTrackerId);
    }

    @Override
    @Transactional
    public void add(List<OncallSuggestionDTO> oncallSuggestionDTOs) {
        for (OncallSuggestionDTO suggestionDTO : oncallSuggestionDTOs) {
            OncallSuggestion suggestion = OncallSuggestion.builder()
                    .oncallTracker(OncallTrackerDTO.mapToEntity(suggestionDTO.getOncallTracker()))
                    .suggestion(suggestionDTO.getSuggestion())
                    .build();

            oncallSuggestionDAO.add(suggestion);
        }
    }
}
