package ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.config;

import ar.com.lcapello.uala.challenge.slices.tweet.domain.exception.InvalidTweetException;
import ar.com.lcapello.uala.challenge.slices.tweet.domain.exception.InvalidTweetIdException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Map;
import org.jboss.logging.Logger;


@Provider
public class TweetExceptionMapper {

    private static final Logger LOG = Logger.getLogger(TweetExceptionMapper.class);

    @ServerExceptionMapper
    public Response handleInvalidTweet(InvalidTweetException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "error", "Invalid Tweet",
                        "message", ex.getMessage()
                ))
                .build();
    }

    @ServerExceptionMapper
    public Response handleInvalidTweetIdException(InvalidTweetIdException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "error", "Invalid TweetId",
                        "message", ex.getMessage()
                ))
                .build();
    }

}