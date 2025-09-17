package ar.com.lcapello.uala.challenge.slices.follower.infrastructure.config;

import ar.com.lcapello.uala.challenge.slices.follower.domain.exception.InvalidFollowException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Map;

@Provider
public class FollowerExceptionMapper {

    @ServerExceptionMapper
    public Response handleInvalidFollow(InvalidFollowException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "error", "Invalid Follow",
                        "message", ex.getMessage()
                ))
                .build();
    }
}


