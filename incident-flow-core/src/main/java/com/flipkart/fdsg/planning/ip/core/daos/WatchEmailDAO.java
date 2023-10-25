package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.WatchEmail;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class WatchEmailDAO extends AbstractDAO<WatchEmail> {

    @Inject
    public WatchEmailDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<WatchEmail> findAll() {
        String queryName = "WatchEmail.findAll";
        Query<WatchEmail> query = currentSession().createNamedQuery(queryName, WatchEmail.class);
        return query.getResultList();
    }

    public void addEmail(String email) {
        WatchEmail watchEmail = new WatchEmail();
        watchEmail.setEmail(email);
        persist(watchEmail);
    }

    public void deleteEmail(String email) {
        String queryName = "WatchEmail.deleteByEmail";
        Query query = currentSession().createNamedQuery(queryName);
        query.setParameter("email", email);
        query.executeUpdate();
    }


    public WatchEmail findByEmail(String email) {
        String queryName = "WatchEmail.findByEmail";
        Query<WatchEmail> query = currentSession().createNamedQuery(queryName, WatchEmail.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }
}
