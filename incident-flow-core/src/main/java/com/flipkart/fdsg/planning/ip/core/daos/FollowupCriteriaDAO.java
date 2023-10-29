package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.FollowupCriteria;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class FollowupCriteriaDAO extends AbstractDAO<FollowupCriteria> {

    @Inject
    public FollowupCriteriaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<FollowupCriteria> findAll() {
        String queryName = "FollowupCriteria.findAll";
        Query<FollowupCriteria> query = currentSession().createNamedQuery(queryName, FollowupCriteria.class);
        return query.getResultList();
    }

    public Optional<FollowupCriteria> findLatestFollowupCriteria() {
        String queryName = "FollowupCriteria.findLatest";
        Query<FollowupCriteria> query = currentSession().createNamedQuery(queryName, FollowupCriteria.class);
        query.setMaxResults(1); // Retrieve only one result
        return Optional.ofNullable(query.uniqueResult());
    }

    public void addFollowupCriteria(FollowupCriteria followupCriteria) {
        persist(followupCriteria);
    }

    public void updateExpectedDurationOfLatestEntry(Long newExpectedDuration) {
        Optional<FollowupCriteria> latestFollowupCriteria = findLatestFollowupCriteria();

        latestFollowupCriteria.ifPresent(criteria -> {
            criteria.setExpectedDuration(newExpectedDuration);
        });
    }
}
