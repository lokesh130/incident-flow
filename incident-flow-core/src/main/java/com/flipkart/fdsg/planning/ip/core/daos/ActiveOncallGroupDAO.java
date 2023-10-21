package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.ActiveOncallGroup;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ActiveOncallGroupDAO extends AbstractDAO<ActiveOncallGroup> {

    @Inject
    public ActiveOncallGroupDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<ActiveOncallGroup> findAll() {
        String queryName = "ActiveOncallGroup.findAll";
        Query<ActiveOncallGroup> query = currentSession().createNamedQuery(queryName, ActiveOncallGroup.class);
        return query.getResultList();
    }

    public Optional<ActiveOncallGroup> findLatestActiveGroup() {
        String queryName = "ActiveOncallGroup.findLatestActiveGroup";
        Query<ActiveOncallGroup> query = currentSession().createNamedQuery(queryName, ActiveOncallGroup.class);
        List<ActiveOncallGroup> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
