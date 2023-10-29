package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallSummary;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class OncallSuggestionDAO extends AbstractDAO<OncallSummary> {

    @Inject
    public OncallSuggestionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<OncallSummary> findAll() {
        String queryName = "OncallSuggestions.findAll";
        Query<OncallSummary> query = currentSession().createNamedQuery(queryName, OncallSummary.class);
        return query.getResultList();
    }

    public List<OncallSummary> findByOncallTrackerId(Long oncallTrackerId) {
        String queryName = "OncallSuggestions.findByOncallTrackerId";
        Query<OncallSummary> query = currentSession().createNamedQuery(queryName, OncallSummary.class);
        query.setParameter("oncallTrackerId", oncallTrackerId);
        return query.getResultList();
    }
    public void add(OncallSummary oncallSummary) {
        currentSession().save(oncallSummary);
    }
}
