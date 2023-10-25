package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.Alert;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class AlertDAO extends AbstractDAO<Alert> {

    @Inject
    public AlertDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Alert> findAll() {
        String queryName = "Alert.find.all";
        Query<Alert> query = currentSession().createNamedQuery(queryName, Alert.class);
        return query.getResultList();
    }

    public Alert findById(int alertId) {
        String queryName = "Alert.find.by.id";
        Query<Alert> query = currentSession().createNamedQuery(queryName, Alert.class);
        query.setParameter("alertId", alertId);
        return query.uniqueResult();
    }
}
