package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.OncallSuggestionDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallSummaryDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallSummary;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
public class DefaultOncallSummaryService implements OncallSummaryService {

    private final OncallSuggestionDAO oncallSuggestionDAO;

    @Inject
    public DefaultOncallSummaryService(OncallSuggestionDAO oncallSuggestionDAO) {
        this.oncallSuggestionDAO = oncallSuggestionDAO;
    }

    @Override
    @Transactional
    public List<OncallSummary> findAll() {
        return oncallSuggestionDAO.findAll();
    }

    @Override
    @Transactional
    public List<OncallSummary> findByOncallTrackerId(Long oncallTrackerId) {
        return oncallSuggestionDAO.findByOncallTrackerId(oncallTrackerId);
    }

    @Override
    @Transactional
    public void add(List<OncallSummaryDTO> oncallSummaryDTOS) {
        for (OncallSummaryDTO suggestionDTO : oncallSummaryDTOS) {
            log.info("Creating OncallSummary from OncallSummaryDTO: {}", suggestionDTO);

            OncallSummary suggestion = OncallSummary.builder()
                    .oncallTracker(OncallTrackerDTO.mapToEntity(suggestionDTO.getOncallTracker()))
                    .suggestion(suggestionDTO.getSuggestion())
                    .build();

            log.debug("Adding OncallSummary to database: {}", suggestion);
            oncallSuggestionDAO.add(suggestion);
            log.info("OncallSummary added successfully.");
        }
    }
}
