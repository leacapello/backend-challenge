package ar.com.lcapello.uala.challenge.platform.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Map;

@Provider
public class GlobalExceptionMapper {

    @ServerExceptionMapper
    public Response handleException(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of(
                        "error", "internal_error",
                        "message", ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper
    public Response handleNotFoundException(NotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of(
                        "error", "not_found",
                        "message", ex.getMessage()
                ))
                .build();
    }
}