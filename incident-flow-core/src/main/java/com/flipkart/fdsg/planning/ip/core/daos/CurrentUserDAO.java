package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.CurrentUser;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class CurrentUserDAO extends AbstractDAO<CurrentUser> {

    @Inject
    public CurrentUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<CurrentUser> findAll() {
        String queryName = "CurrentUser.findAll";
        Query<CurrentUser> query = currentSession().createNamedQuery(queryName, CurrentUser.class);
        return query.getResultList();
    }

    public List<CurrentUser> findByOncallTrackerId(Long oncallTrackerId) {
        String queryName = "CurrentUser.findByOncallTrackerId";
        Query<CurrentUser> query = currentSession().createNamedQuery(queryName, CurrentUser.class);
        query.setParameter("oncallTrackerId", oncallTrackerId);
        return query.getResultList();
    }

    public void add(CurrentUser currentUser) {
        currentSession().save(currentUser);
    }
}
