package com.flipkart.fdsg.planning.ip.ws.resources;

import com.flipkart.fdsg.planning.ip.core.dtos.*;
import com.flipkart.fdsg.planning.ip.core.entities.ActiveOncallGroup;
import com.flipkart.fdsg.planning.ip.core.exceptions.RecordNotFoundException;
import com.flipkart.fdsg.planning.ip.core.services.*;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Path("/v1/oncall")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Api(value = "oncall")
public class OncallResource {

    private final ActiveOncallGroupService activeOncallGroupService;
    private final OncallTrackerService oncallTrackerService;
    private final OncallSuggestionService oncallSuggestionService;
    private final CurrentUserService currentUserService;
    private final HistoricalUserService historicalUserService;
    private final FollowupService followupService;
    private final AlertService alertService;
    private final WatchEmailService watchEmailService;
    private final SyncService syncService;

    @GET
    @Path("/active-oncall-group")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getLatestActiveOncallGroup() {
        log.info("Getting the latest active on-call group");

        try {
            ActiveOncallGroup latestActiveGroup = activeOncallGroupService.findLatestActiveGroup()
                    .orElseThrow(() -> new RecordNotFoundException(ActiveOncallGroup.class, "No active on-call group found"));

            return Response.ok(ActiveOncallGroupDTO.map(latestActiveGroup)).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting the latest active on-call group: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/active-oncall-group/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getActiveOncallGroupByDate(@PathParam("date") String date) {
        log.info("Getting the latest active on-call group");
        log.info("Date: {}", date);
        LocalDateTime givenDate = LocalDateTime.parse(date);
        try {
            ActiveOncallGroup latestActiveGroup = activeOncallGroupService.findActiveGroupByDate(givenDate)
                    .orElseThrow(() -> new RecordNotFoundException(ActiveOncallGroup.class, "No active on-call group found"));

            return Response.ok(ActiveOncallGroupDTO.map(latestActiveGroup)).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting the latest active on-call group: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/oncall-trackers/{oncall_group_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getOncallTrackers(@PathParam("oncall_group_id") Long oncallGroupId) {
        log.info("Getting all on-call trackers");

        try {
            List<OncallTrackerDTO> oncallTrackers = oncallTrackerService.findByOncallGroupId(oncallGroupId)
                    .stream().map(OncallTrackerDTO::map).collect(Collectors.toList());

            return Response.ok(oncallTrackers).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting on-call trackers: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/oncall-suggestions/{oncall_tracker_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getOncallSuggestions(@PathParam("oncall_tracker_id") Long oncallTrackerId) {
        log.info("Getting all on-call suggestions");

        try {
            List<OncallSuggestionDTO> oncallSuggestions = oncallSuggestionService.findByOncallTrackerId(oncallTrackerId)
                    .stream().map(OncallSuggestionDTO::map).collect(Collectors.toList());

            return Response.ok(oncallSuggestions).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting on-call suggestions: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/historical-users/{oncall_tracker_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getHistoricalUser(@PathParam("oncall_tracker_id") Long oncallTrackerId) {
        log.info("Getting all historical users");

        try {
            List<HistoricalUserDTO> historicalUsers = historicalUserService.findByOncallTrackerId(oncallTrackerId)
                    .stream().map(HistoricalUserDTO::map).collect(Collectors.toList());

            return Response.ok(historicalUsers).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting historical users: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/current-users/{oncall_tracker_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getCurrentUser(@PathParam("oncall_tracker_id") Long oncallTrackerId) {
        log.info("Getting all current users");

        try {
            List<CurrentUserDTO> currentUsers = currentUserService.findByOncallTrackerId(oncallTrackerId)
                    .stream().map(CurrentUserDTO::map).collect(Collectors.toList());

            return Response.ok(currentUsers).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting current users: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/followups")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getAllFollowups() {
        log.info("Getting all unacknowledged followups");

        try {
            List<FollowupDTO> allFollowups = followupService.findAll()
                    .stream().map(FollowupDTO::map).collect(Collectors.toList());

            return Response.ok(allFollowups).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting unacknowledged followups: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/alerts")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getAllAlerts() {
        log.info("Getting all unacknowledged alerts");

        try {
            List<AlertDTO> allAlerts = alertService.findAll()
                    .stream().map(AlertDTO::map).collect(Collectors.toList());

            return Response.ok(allAlerts).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting unacknowledged alerts: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/watch-emails")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getAllWatchEmails() {
        log.info("Getting all watch emails");

        try {
            List<WatchEmailDTO> allWatchEmails = watchEmailService.getAllEmails()
                    .stream().map(WatchEmailDTO::map).collect(Collectors.toList());

            return Response.ok(allWatchEmails).build();
        } catch (RecordNotFoundException e) {
            log.error("Error while getting watch emails: {}", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/watch-emails")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response addWatchEmail(WatchEmailDTO watchEmailDTO) {
        log.info("Adding watch email");

        try {
            watchEmailService.addEmail(watchEmailDTO.getEmail());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            log.error("Error while adding watch email: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/watch-emails/{email}")
    @UnitOfWork
    public Response deleteWatchEmail(@PathParam("email") String email) {
        log.info("Deleting watch email");

        try {
            watchEmailService.deleteEmail(email);
            return Response.ok().build();
        } catch (Exception e) {
            log.error("Error while deleting watch email: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/sync-emails")
    @UnitOfWork
    public Response syncEmails() {
        log.info("Initiating email synchronization");

        try {
            syncService.syncEmails();
            return Response.ok("Email synchronization initiated.").build();
        } catch (Exception e) {
            log.error("Error during email synchronization: {}", e.getMessage());
            return Response.serverError().entity("Error during email synchronization: " + e.getMessage()).build();
        }
    }

}
