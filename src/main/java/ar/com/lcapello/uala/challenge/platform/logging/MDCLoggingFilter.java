package ar.com.lcapello.uala.challenge.platform.logging;

import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import java.util.UUID;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

@Provider
public class MDCLoggingFilter {

    private static final Logger LOG = Logger.getLogger(MDCLoggingFilter.class);

    private static final String HEADER_REQUEST_ID = "X-Request-Id";
    private static final String MDC_REQUEST_ID = "requestId";
    private static final String MDC_METHOD = "method";
    private static final String MDC_PATH = "path";

    @ServerRequestFilter
    public void onRequest(ContainerRequestContext requestContext) {
        String requestId = requestContext.getHeaderString(HEADER_REQUEST_ID);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(MDC_REQUEST_ID, requestId);
        MDC.put(MDC_METHOD, requestContext.getMethod());
        MDC.put(MDC_PATH, requestContext.getUriInfo().getPath());
    }

    @ServerResponseFilter
    public void onResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String method = String.valueOf(MDC.get(MDC_METHOD));
        String path = String.valueOf(MDC.get(MDC_PATH));
        String requestId = String.valueOf(MDC.get(MDC_REQUEST_ID));
        int status = responseContext.getStatus();

        responseContext.getHeaders().putSingle(HEADER_REQUEST_ID, requestId);

        if (status < 200 || status >= 300) {
            LOG.errorf("Petición %s %s id=%s finalizó con status=%d",
                    method, path, requestId, status);
        }

        MDC.clear();
    }
}