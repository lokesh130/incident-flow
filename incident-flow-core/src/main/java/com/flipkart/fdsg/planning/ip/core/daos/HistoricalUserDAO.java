package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.HistoricalUser;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class HistoricalUserDAO extends AbstractDAO<HistoricalUser> {

    @Inject
    public HistoricalUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<HistoricalUser> findAll() {
        String queryName = "HistoricalUsers.findAll";
        Query<HistoricalUser> query = currentSession().createNamedQuery(queryName, HistoricalUser.class);
        return query.getResultList();
    }

    public List<HistoricalUser> findByOncallTrackerId(Long oncallTrackerId) {
        String queryName = "HistoricalUsers.findByOncallTrackerId";
        Query<HistoricalUser> query = currentSession().createNamedQuery(queryName, HistoricalUser.class);
        query.setParameter("oncallTrackerId", oncallTrackerId);
        return query.getResultList();
    }
}
