package com.flipkart.fdsg.planning.ip.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.jackson.Jackson;

import javax.inject.Provider;

public class ObjectMapperProvider implements Provider<ObjectMapper> {

    private static ObjectMapper objectMapper = Jackson.newObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public ObjectMapper get() {
        return objectMapper;
    }
}
