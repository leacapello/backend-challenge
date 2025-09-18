package ar.com.lcapello.uala.challenge.platform.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import java.util.Map;

@Provider
public class DBExceptionMapper {

    @ServerExceptionMapper
    public Response handleConstraintViolationException(ConstraintViolationException ex) {
        return Response.status(Response.Status.CONFLICT)
                .entity(Map.of(
                        "error", "conflict",
                        "message", ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper
    public Response handleEntityNotFoundException(EntityNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of(
                        "error", "not_found",
                        "message", ex.getMessage()
                ))
                .build();
    }

}