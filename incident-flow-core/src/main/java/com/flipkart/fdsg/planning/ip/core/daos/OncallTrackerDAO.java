package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class OncallTrackerDAO extends AbstractDAO<OncallTracker> {

    @Inject
    public OncallTrackerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<OncallTracker> findAll() {
        String queryName = "OncallTracker.findAll";
        Query<OncallTracker> query = currentSession().createNamedQuery(queryName, OncallTracker.class);
        return query.getResultList();
    }

    public List<OncallTracker> findByPrimaryUserId(Long primaryUserId) {
        String queryName = "OncallTracker.findByPrimaryUserId";
        Query<OncallTracker> query = currentSession().createNamedQuery(queryName, OncallTracker.class);
        query.setParameter("primaryUserId", primaryUserId);
        return query.getResultList();
    }

    public List<OncallTracker> findBySecondaryUserId(Long secondaryUserId) {
        String queryName = "OncallTracker.findBySecondaryUserId";
        Query<OncallTracker> query = currentSession().createNamedQuery(queryName, OncallTracker.class);
        query.setParameter("secondaryUserId", secondaryUserId);
        return query.getResultList();
    }

    public List<OncallTracker> findByPrimaryAndSecondaryUserId(Long primaryUserId, Long secondaryUserId) {
        String queryName = "OncallTracker.findByPrimaryAndSecondaryUserId";
        Query<OncallTracker> query = currentSession().createNamedQuery(queryName, OncallTracker.class);
        query.setParameter("primaryUserId", primaryUserId);
        query.setParameter("secondaryUserId", secondaryUserId);
        return query.getResultList();
    }

    public List<OncallTracker> findByOncallGroupId(Long oncallGroupId) {
        String queryName = "OncallTracker.findByOncallGroupId";
        Query<OncallTracker> query = currentSession().createNamedQuery(queryName, OncallTracker.class);
        query.setParameter("oncallGroupId", oncallGroupId);
        return query.getResultList();
    }

    public OncallTracker findByThreadId(String threadId) {
        String queryName = "OncallTracker.findByThreadId";
        Query<OncallTracker> query = currentSession().createNamedQuery(queryName, OncallTracker.class);
        query.setParameter("threadId", threadId);
        List<OncallTracker> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public OncallTracker add(OncallTrackerDTO dto) {
        OncallTracker oncallTracker = OncallTrackerDTO.mapToEntity(dto);
        persist(oncallTracker);
        return oncallTracker;
    }
}
