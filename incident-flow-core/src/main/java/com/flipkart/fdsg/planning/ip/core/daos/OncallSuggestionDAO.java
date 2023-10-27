package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallSuggestion;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class OncallSuggestionDAO extends AbstractDAO<OncallSuggestion> {

    @Inject
    public OncallSuggestionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<OncallSuggestion> findAll() {
        String queryName = "OncallSuggestions.findAll";
        Query<OncallSuggestion> query = currentSession().createNamedQuery(queryName, OncallSuggestion.class);
        return query.getResultList();
    }

    public List<OncallSuggestion> findByOncallTrackerId(Long oncallTrackerId) {
        String queryName = "OncallSuggestions.findByOncallTrackerId";
        Query<OncallSuggestion> query = currentSession().createNamedQuery(queryName, OncallSuggestion.class);
        query.setParameter("oncallTrackerId", oncallTrackerId);
        return query.getResultList();
    }
    public void add(OncallSuggestion oncallSuggestion) {
        currentSession().save(oncallSuggestion);
    }
}
