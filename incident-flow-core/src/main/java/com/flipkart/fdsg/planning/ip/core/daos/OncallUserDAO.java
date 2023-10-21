package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallUser;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class OncallUserDAO extends AbstractDAO<OncallUser> {

    @Inject
    public OncallUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<OncallUser> findAll() {
        String queryName = "OncallUser.findAll";
        Query<OncallUser> query = currentSession().createNamedQuery(queryName, OncallUser.class);
        return query.getResultList();
    }

    public Optional<String> findNextUserIdById(Long currentId) {
        String queryName = "OncallUser.findNextUserIdById";
        Query<String> query = currentSession().createNamedQuery(queryName, String.class);
        query.setParameter("currentId", currentId);
        return Optional.ofNullable(query.uniqueResult());
    }
}
