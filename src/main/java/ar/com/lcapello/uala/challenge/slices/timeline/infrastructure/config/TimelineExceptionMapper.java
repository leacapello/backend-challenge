package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.config;

import ar.com.lcapello.uala.challenge.slices.follower.domain.exception.InvalidFollowException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Map;

@Provider
public class TimelineExceptionMapper {

    @ServerExceptionMapper
    public Response handleInvalidFollow(BadRequestException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "error", "bad_request",
                        "message", ex.getMessage()
                ))
                .build();
    }
    
}