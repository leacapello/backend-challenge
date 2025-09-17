package ar.com.lcapello.uala.challenge.platform.security;

import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

@Provider
public class UserValidationFilter {

    public static final String HEADER_USER_ID = "X-User-Id";

    @ServerRequestFilter(preMatching = true)
    public void validateUserId(ContainerRequestContext requestContext) {
        String userId = requestContext.getHeaderString(HEADER_USER_ID);

        if (userId == null || userId.isBlank()) {
            requestContext.abortWith(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Missing required user header: " + HEADER_USER_ID)
                            .build()
            );
        }
    }
}