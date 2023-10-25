package com.flipkart.fdsg.planning.ip.core.daos;

import com.flipkart.fdsg.planning.ip.core.entities.MessageConfiguration;
import com.flipkart.persistence.dao.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class MessageConfigurationDAO extends AbstractDAO<MessageConfiguration> {

    @Inject
    public MessageConfigurationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void addMessageConfiguration(MessageConfiguration messageConfiguration) {
        currentSession().save(messageConfiguration);
    }

    public void deleteMessageConfiguration(MessageConfiguration messageConfiguration) {
        currentSession().delete(messageConfiguration);
    }

    public MessageConfiguration getMessageConfigurationById(Long id) {
        String queryName = "MessageConfiguration.findById";
        Query<MessageConfiguration> query = currentSession().createNamedQuery(queryName, MessageConfiguration.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    public List<MessageConfiguration> getAllMessageConfigurations() {
        String queryName = "MessageConfiguration.findAll";
        Query<MessageConfiguration> query = currentSession().createNamedQuery(queryName, MessageConfiguration.class);
        return query.getResultList();
    }
}