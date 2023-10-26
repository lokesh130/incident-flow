package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
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

    public List<RawEmail> findById(Long id) {
        String queryName = "Emails.findById";
        Query<RawEmail> query = currentSession().createNamedQuery(queryName, RawEmail.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public void addRawEmail(RawEmailDTO rawEmailDTO) {
        RawEmail rawEmail = RawEmailDTO.mapToEntity(rawEmailDTO);
        persist(rawEmail);
    }
}
