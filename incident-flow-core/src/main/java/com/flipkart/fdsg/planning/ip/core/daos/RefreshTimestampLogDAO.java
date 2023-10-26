package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.RefreshTimestampLog;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.sql.Timestamp;

public class RefreshTimestampLogDAO extends AbstractDAO<RefreshTimestampLog> {

    @Inject
    public RefreshTimestampLogDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void addRefreshTimestamp(Long refreshTimestamp) {
        RefreshTimestampLog logEntry = RefreshTimestampLog.builder()
                .refreshTimestamp(refreshTimestamp)
                .build();
        persist(logEntry);
    }

    public Long getLatestRefreshTimestamp() {
        String queryName = "RefreshTimestampLog.findLatest";
        Query<RefreshTimestampLog> query = currentSession().createNamedQuery(queryName, RefreshTimestampLog.class);
        query.setMaxResults(1); // Limit to one result (latest record)
        RefreshTimestampLog latestLog = query.uniqueResult();
        return (latestLog != null) ? latestLog.getRefreshTimestamp() : null;
    }
}
