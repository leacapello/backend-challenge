package ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.rest;

import ar.com.lcapello.uala.challenge.slices.tweet.application.command.CreateTweetCommand;
import ar.com.lcapello.uala.challenge.slices.tweet.application.command.CreateTweetCommandHandler;
import ar.com.lcapello.uala.challenge.slices.tweet.application.query.GetTweetByIdHandler;
import ar.com.lcapello.uala.challenge.slices.tweet.application.query.GetTweetByIdQuery;
import ar.com.lcapello.uala.challenge.slices.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.rest.dto.CreateTweetRequest;
import ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.rest.dto.TweetResponse;
import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/tweets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TweetResource {

    @Inject
    CreateTweetCommandHandler createTweetCommandHandler;

    @Inject
    GetTweetByIdHandler getTweetByIdHandler;

    @POST
    @Path("/{tweetID}")
    @Transactional
    public Response create(@PathParam("tweetID") String tweetID,
                           @HeaderParam("X-User-Id") String userId,
                           CreateTweetRequest createTweetRequest) {

        CreateTweetCommand command = new CreateTweetCommand(tweetID, userId, createTweetRequest.message());
        Tweet tweet = createTweetCommandHandler.handle(command);

        TweetResponse tweetResponse = new TweetResponse(
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
    public Response getTweet(@PathParam("tweetID") String tweetID,
                             @HeaderParam("X-User-Id") String userId) {

        TweetResponse result = getTweetData(tweetID);
        return Response.ok(result).build();
    }

    @CacheResult(cacheName = "get-tweet-cache")
    public TweetResponse getTweetData(String tweetID) {
        Tweet tweet = getTweetByIdHandler.handle(new GetTweetByIdQuery(tweetID))
                .orElseThrow(() -> new EntityNotFoundException("Tweet not found"));

        return new TweetResponse(
                tweet.getTweetID().value(),
                tweet.getAuthorID(),
                tweet.getMessage(),
                tweet.getCreatedAt()
        );
    }
}