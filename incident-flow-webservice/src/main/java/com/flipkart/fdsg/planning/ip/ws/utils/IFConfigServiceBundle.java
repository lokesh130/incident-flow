package com.flipkart.fdsg.planning.ip.ws.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.flipkart.dropwizard.FkDropwizardConfigurationFactoryFactory;
import com.flipkart.fdsg.planning.ip.ws.config.IFConfigServiceConfigurationSourceProvider;
import io.dropwizard.Bundle;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Configures Dropwizard application to read configuration directly from either of <ul>
 * <li>FK Config Service bucket</li>
 * <li>local JSON file (identical to above in structure)</li>
 * </ul>
 *
 * @see <a href="https://github.fkinternal.com/Flipkart/dropwizard-configsvc">dropwizard-configsvc's README</a>
 */
public class IFConfigServiceBundle implements Bundle {

    private final ObjectMapper ymlObjectMapper;
    private final char jsonFlattenSeparator;

    public IFConfigServiceBundle(char jsonFlattenSeparator) {
        ymlObjectMapper = Jackson.newObjectMapper(new YAMLFactory())
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.jsonFlattenSeparator = jsonFlattenSeparator;
    }

    public IFConfigServiceBundle() {
        this('-');
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(Bootstrap bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new IFConfigServiceConfigurationSourceProvider(jsonFlattenSeparator, bootstrap.getObjectMapper(),
                        ymlObjectMapper));
        bootstrap.setConfigurationFactoryFactory(new FkDropwizardConfigurationFactoryFactory(jsonFlattenSeparator));
    }

    @Override
    public void run(Environment environment) {
        //nothing to do ..check initialize method
    }

    ObjectMapper getYmlObjectMapper() {
        return ymlObjectMapper;
    }
}


