package ar.com.lcapello.uala.challenge.timeline.infrastructure.rest;

import ar.com.lcapello.uala.challenge.timeline.application.query.GetTimelineByFollowerHandler;
import ar.com.lcapello.uala.challenge.timeline.application.query.GetTimelineByFollowerQuery;
import ar.com.lcapello.uala.challenge.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.timeline.infrastructure.rest.dto.TimelineResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/timeline")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TimelineResource {

    @Inject
    GetTimelineByFollowerHandler getTimelineHandler;

    @GET
    public Response getTimeline(@HeaderParam("X-User-Id") String userId,
                           @QueryParam("page_size") Integer pageSize,
                           @QueryParam("page") Integer page) {

        final GetTimelineByFollowerQuery query = new GetTimelineByFollowerQuery(userId, pageSize, page);

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