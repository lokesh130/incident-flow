package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.OncallSuggestionDAO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallSuggestion;

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
}
