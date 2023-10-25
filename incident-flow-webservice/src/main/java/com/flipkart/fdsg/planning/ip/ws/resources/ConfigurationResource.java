package com.flipkart.fdsg.planning.ip.ws.resources;

import com.flipkart.fdsg.planning.ip.core.dtos.MessageConfigurationDTO;
import com.flipkart.fdsg.planning.ip.core.entities.MessageConfiguration;
import com.flipkart.fdsg.planning.ip.core.exceptions.RecordNotFoundException;
import com.flipkart.fdsg.planning.ip.core.services.MessageConfigurationService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Path("/v1/configurations")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Api(value = "configurations")
public class ConfigurationResource {

    private final MessageConfigurationService messageConfigurationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response addConfiguration(MessageConfigurationDTO configurationDTO) {
        log.info("Adding configuration");

        try {
            messageConfigurationService.addMessageConfiguration(configurationDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            log.error("Error while adding configuration: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteConfiguration(@PathParam("id") Long id) {
        log.info("Deleting configuration");

        try {
            messageConfigurationService.deleteMessageConfiguration(id);
            return Response.ok().build();
        } catch (RecordNotFoundException e) {
            log.error("Error while deleting configuration: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Error while deleting configuration: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response updateConfiguration(@PathParam("id") Long id, MessageConfigurationDTO configurationDTO) {
        log.info("Updating configuration");

        try {
            MessageConfiguration existingConfiguration = messageConfigurationService.getMessageConfigurationById(id);
            if (existingConfiguration != null) {
                if (configurationDTO.getSubjectRegex() != null) {
                    existingConfiguration.setSubjectRegex(configurationDTO.getSubjectRegex());
                }

                if (configurationDTO.getFrequency() != null) {
                    existingConfiguration.setFrequency(configurationDTO.getFrequency());
                }

                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Configuration not found").build();
            }
        } catch (Exception e) {
            log.error("Error while updating configuration: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getAllConfigurations() {
        log.info("Getting all configurations");

        try {
            List<MessageConfigurationDTO> allConfigurations = messageConfigurationService.getAllMessageConfigurations()
                    .stream().map(MessageConfigurationDTO::map).collect(Collectors.toList());

            return Response.ok(allConfigurations).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting configurations: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
