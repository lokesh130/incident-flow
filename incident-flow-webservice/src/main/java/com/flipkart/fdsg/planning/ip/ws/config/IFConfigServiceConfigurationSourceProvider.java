package com.flipkart.fdsg.planning.ip.ws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.fdsg.planning.ip.core.config.IFGenericConfigurationProvider;
import io.dropwizard.configuration.ConfigurationSourceProvider;

/**
 * Reads Dropwizard configuration file with following behavior:<ul>
 * <li>If file has a '.json' extension, reads it as flattened json</li>
 * <li>If file has a '.yml' extension, converts it into a flattened json</li>
 * <li>If file has any other extension, throws an exception</li>
 * </ul>
 */
public class IFConfigServiceConfigurationSourceProvider extends
        IFGenericConfigurationProvider implements
        ConfigurationSourceProvider {

    public IFConfigServiceConfigurationSourceProvider(char jsonFlattenSeparator, ObjectMapper jsonObjectMapper,
                                                      ObjectMapper ymlObjectMapper) {
        super(jsonFlattenSeparator, jsonObjectMapper, ymlObjectMapper);
    }
}
