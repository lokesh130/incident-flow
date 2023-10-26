package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.RawEmail;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class RawEmailDAO extends AbstractDAO<RawEmail> {

    @Inject
    public RawEmailDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<RawEmail> findAll() {
        String queryName = "Emails.findAll";
        Query<RawEmail> query = currentSession().createNamedQuery(queryName, RawEmail.class);
        return query.getResultList();
    }

    public List<RawEmail> findBySubject(String subject) {
        String queryName = "Emails.findBySubject";
        Query<RawEmail> query = currentSession().createNamedQuery(queryName, RawEmail.class);
        query.setParameter("subject", subject);
        return query.getResultList();
    }


    public List<RawEmail> findByFromList(String fromList) {
        String queryName = "Emails.findByFromList";
        Query<RawEmail> query = currentSession().createNamedQuery(queryName, RawEmail.class);
        query.setParameter("fromList", fromList);
        return query.getResultList();
    }

    public List<RawEmail> findByToList(String toList) {
        String queryName = "Emails.findByToList";
        Query<RawEmail> query = currentSession().createNamedQuery(queryName, RawEmail.class);
        query.setParameter("toList", toList);
        return query.getResultList();
    }

    public List<RawEmail> findByThreadId(String threadId) {
        String queryName = "Emails.findByThreadId";
        Query<RawEmail> query = currentSession().createNamedQuery(queryName, RawEmail.class);
        query.setParameter("threadId", threadId);
        return query.getResultList();
    }

    public List<RawEmail> findByMessageId(String messageId) {
        String queryName = "Emails.findByMessageId";
        Query<RawEmail> query = currentSession().createNamedQuery(queryName, RawEmail.class);
        query.setParameter("messageId", messageId);
        return query.getResultList();
    }
}
