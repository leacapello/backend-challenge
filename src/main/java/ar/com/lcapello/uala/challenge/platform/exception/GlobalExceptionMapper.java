package ar.com.lcapello.uala.challenge.platform.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import jakarta.ws.rs.NotFoundException;
import java.util.Map;

@Provider
public class GlobalExceptionMapper {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

    @ServerExceptionMapper
    public Response handleAnyException(Exception ex) {
        if (containsCause(ex, ConstraintViolationException.class)) {
            LOG.error("Constraint violation", ex);
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of(
                            "error", "constraint_violation",
                            "message", "constraint violated"
                    ))
                    .build();
        }

        LOG.error("Unhandled exception", ex);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of(
                        "error", "internal_error",
                        "message", ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper
    public Response handleNotFoundException(NotFoundException ex) {
        LOG.error("not found exception", ex);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of(
                        "error", "not_found",
                        "message", ex.getMessage()
                ))
                .build();
    }

    private boolean containsCause(Throwable ex, Class<? extends Throwable> type) {
        Throwable current = ex;
        while (current != null) {
            if (type.isInstance(current)) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

}