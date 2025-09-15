package ar.com.lcapello.uala.challenge.tweet.infrastructure.rest;

import ar.com.lcapello.uala.challenge.tweet.application.command.CreateTweetCommand;
import ar.com.lcapello.uala.challenge.tweet.application.command.CreateTweetCommandHandler;
import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.infrastructure.rest.dto.CreateTweetRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ar.com.lcapello.uala.challenge.tweet.infrastructure.rest.dto.TweetResponse;

import java.net.URI;

@Path("/tweets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TweetResource {

    @Inject
    private CreateTweetCommandHandler createTweetCommandHandler;

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
                tweet.getAuthorID().value(),
                tweet.getMessage(),
                tweet.getCreatedAt()
        );

        return Response.created(URI.create("/tweets/" + tweetID))
                .entity(tweetResponse)
                .build();
    }

}