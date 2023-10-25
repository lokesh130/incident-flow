package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.Followup;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class FollowupDAO extends AbstractDAO<Followup> {

    @Inject
    public FollowupDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Followup> findAll() {
        String queryName = "Followup.find.all";
        Query<Followup> query = currentSession().createNamedQuery(queryName, Followup.class);
        return query.getResultList();
    }

    public Followup findById(int followupId) {
        String queryName = "Followup.find.by.id";
        Query<Followup> query = currentSession().createNamedQuery(queryName, Followup.class);
        query.setParameter("followupId", followupId);
        return query.uniqueResult();
    }
}
