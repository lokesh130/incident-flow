package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.MessageConfigurationDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.MessageConfigurationDTO;
import com.flipkart.fdsg.planning.ip.core.entities.MessageConfiguration;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultMessageConfigurationService implements MessageConfigurationService {

    private final MessageConfigurationDAO messageConfigurationDAO;

    @Inject
    public DefaultMessageConfigurationService(MessageConfigurationDAO messageConfigurationDAO) {
        this.messageConfigurationDAO = messageConfigurationDAO;
    }

    @Override
    @Transactional
    public void addMessageConfiguration(MessageConfigurationDTO messageConfigurationDTO) {
        MessageConfiguration messageConfiguration = MessageConfiguration.builder()
                .subjectRegex(messageConfigurationDTO.getSubjectRegex())
                .frequency(messageConfigurationDTO.getFrequency())
                .build();
        messageConfigurationDAO.addMessageConfiguration(messageConfiguration);
    }

    @Override
    @Transactional
    public void deleteMessageConfiguration(Long id) {
        MessageConfiguration messageConfiguration = messageConfigurationDAO.getMessageConfigurationById(id);
        if (messageConfiguration != null) {
            messageConfigurationDAO.deleteMessageConfiguration(messageConfiguration);
        }
    }

    @Override
    public MessageConfiguration getMessageConfigurationById(Long id) {
        return messageConfigurationDAO.getMessageConfigurationById(id);
    }

    @Override
    public List<MessageConfiguration> getAllMessageConfigurations() {
        return messageConfigurationDAO.getAllMessageConfigurations();
    }
}
