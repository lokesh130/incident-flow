package com.flipkart.fdsg.planning.ip.ws.resources;

import com.flipkart.fdsg.planning.ip.ws.Constants;
import com.flipkart.fdsg.planning.ip.ws.config.IncidentFlowConfiguration;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Default resource. Required for testlib and reflective access. Don't remove.
 */
@Path("/")
@Singleton
@Api(value = "/")
@Slf4j
public class DefaultResource {
    private final IncidentFlowConfiguration configuration;

    @Inject
    public DefaultResource(IncidentFlowConfiguration configuration) {

        this.configuration = configuration;
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String welcome() {
        return String.format("%s%n%nThis environment is marked as '%s'", Constants.WEB_SERVICE_NAME, configuration.getEnv());
    }
}
