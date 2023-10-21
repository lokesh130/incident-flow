package com.flipkart.fdsg.planning.ip.ws.config;

import com.flipkart.configsvc.clients.ConfigurationProvider;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;

public class IFSubstitutingSourceProvider extends
        SubstitutingSourceProvider implements
        ConfigurationProvider {

    public IFSubstitutingSourceProvider(ConfigurationSourceProvider configurationSourceProvider, EnvironmentVariableSubstitutor environmentVariableSubstitutor) {
        super(configurationSourceProvider, environmentVariableSubstitutor);
    }
}
