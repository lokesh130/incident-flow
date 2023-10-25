package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.MessageConfigurationDTO;
import com.flipkart.fdsg.planning.ip.core.entities.MessageConfiguration;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageConfigurationService {

    void addMessageConfiguration(MessageConfigurationDTO messageConfigurationDTO);

    void deleteMessageConfiguration(Long id);

    MessageConfiguration getMessageConfigurationById(Long id);

    List<MessageConfiguration> getAllMessageConfigurations();
}
