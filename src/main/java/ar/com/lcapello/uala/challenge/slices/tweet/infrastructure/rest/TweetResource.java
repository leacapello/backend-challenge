package ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.rest;

import ar.com.lcapello.uala.challenge.slices.tweet.application.command.CreateTweetCommand;
import ar.com.lcapello.uala.challenge.slices.tweet.application.command.CreateTweetCommandHandler;
import ar.com.lcapello.uala.challenge.slices.tweet.application.query.GetTweetByIdHandler;
import ar.com.lcapello.uala.challenge.slices.tweet.application.query.GetTweetByIdQuery;
import ar.com.lcapello.uala.challenge.slices.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.rest.dto.CreateTweetRequest;
import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.rest.dto.TweetResponse;
import java.net.URI;

@Path("/tweets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TweetResource {

    @Inject
    private CreateTweetCommandHandler createTweetCommandHandler;

    @Inject
    private GetTweetByIdHandler getTweetByIdHandler;

    @POST
    @Path("/{tweetID}")
    @Transactional
    public Response create(@PathParam("tweetID") String tweetID,
                           @HeaderParam("X-User-Id") String userId,
                           final CreateTweetRequest createTweetRequest) {

        final CreateTweetCommand request = new CreateTweetCommand(
                tweetID, userId, createTweetRequest.message()
        );

        final Tweet tweet = createTweetCommandHandler.handle(request);

        final TweetResponse tweetResponse = new TweetResponse(
                tweet.getTweetID().value(),
                tweet.getAuthorID(),
                tweet.getMessage(),
                tweet.getCreatedAt()
        );

        return Response.created(URI.create("/tweets/" + tweetID))
                .entity(tweetResponse)
                .build();
    }

    @GET
    @Path("/{tweetID}")
    @CacheResult(cacheName = "get-tweet-cache")
    public Response create(@PathParam("tweetID") String tweetID,
                           @HeaderParam("X-User-Id") String userId) {

        final GetTweetByIdQuery query = new GetTweetByIdQuery(tweetID);

        final Tweet tweet = getTweetByIdHandler.handle(query).orElseThrow(() -> new EntityNotFoundException("Tweet not found"));

        final TweetResponse tweetResponse = new TweetResponse(
                tweet.getTweetID().value(),
                tweet.getAuthorID(),
                tweet.getMessage(),
                tweet.getCreatedAt()
        );

        return Response.ok(tweetResponse).build();
    }

}