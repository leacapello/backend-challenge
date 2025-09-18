package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.rest;

import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerQuery;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.rest.dto.TimelineResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/timeline")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TimelineResource {

    @ConfigProperty(name = "timeline.max-page-size")
    private int maxPageSize;

    @Inject
    GetTimelineByFollowerHandler getTimelineHandler;

    @GET
    public Response getTimeline(@HeaderParam("X-User-Id") String userId,
                           @QueryParam("page_size") Integer pageSize,
                           @QueryParam("page") Integer page) {

        if (page == null || page < 1) {
            throw new BadRequestException("Parameter 'page' is required and must be greater than or equal to 1.");
        }

        if (pageSize == null || pageSize < 1 || pageSize > maxPageSize) {
            throw new BadRequestException("Parameter 'page_size' is required and must be between 1 and " + maxPageSize);
        }

        final GetTimelineByFollowerQuery query = new GetTimelineByFollowerQuery(userId, page, pageSize);

        final List<Timeline> timelines = getTimelineHandler.handle(query);

        final List<TimelineResponse> response = timelines.stream().
                map(timeline -> new TimelineResponse(
                        timeline.getTweetId(),
                        timeline.getAuthorId(),
                        timeline.getMessage(),
                        timeline.getCreatedAt()
                ))
                .toList();

        return Response.ok(response).build();
    }

}