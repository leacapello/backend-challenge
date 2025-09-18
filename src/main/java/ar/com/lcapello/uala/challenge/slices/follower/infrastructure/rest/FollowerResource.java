package ar.com.lcapello.uala.challenge.slices.follower.infrastructure.rest;

import ar.com.lcapello.uala.challenge.slices.follower.application.command.AddFollowCommand;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.AddFollowCommandHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.RemoveFollowCommand;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.RemoveFollowCommandHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.query.GetFollowersHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.query.GetFollowersQuery;
import ar.com.lcapello.uala.challenge.slices.follower.application.query.GetFollowingHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.query.GetFollowingQuery;
import ar.com.lcapello.uala.challenge.slices.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.slices.follower.infrastructure.rest.dto.FollowerResponse;
import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/followers")
public class FollowerResource {

    @Inject
    private AddFollowCommandHandler addFollowCommandHandler;

    @Inject
    private RemoveFollowCommandHandler removeFollowCommandHandler;

    @Inject
    private GetFollowersHandler getFollowersHandler;

    @Inject
    private GetFollowingHandler getFollowingHandler;

    @POST
    @Path("/{followedID}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam("X-User-Id") String followerID,
                            @PathParam("followedID") String followedID) {
        final Follow follow = addFollowCommandHandler.handle(new AddFollowCommand(followerID, followedID));

        final FollowerResponse followerResponse = new FollowerResponse(
            follow.getFollowerID(),
            follow.getFollowedID(),
            follow.getCreatedAt()
        );

        return Response.created(URI.create("/followers/" + followedID))
                .entity(followerResponse)
                .build();
    }

    @DELETE
    @Path("/{followedID}")
    @Transactional
    public Response delete(@HeaderParam("X-User-Id") String followerID,
                           @PathParam("followedID") String followedID) {
        removeFollowCommandHandler.handle(new RemoveFollowCommand(followerID, followedID));
        return Response.noContent().build();
    }

    @GET
    @Path("/{followedID}")
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "get-followed-cache")
    public Response getFollowed(@HeaderParam("X-User-Id") String followerID,
                                @PathParam("followedID") String followedID) {
        final Follow follow = getFollowingHandler.handle(new GetFollowingQuery(followerID, followedID))
                .orElseThrow(() -> new EntityNotFoundException("Followed not found"));

        final FollowerResponse followerResponse = new FollowerResponse(
                follow.getFollowerID(),
                follow.getFollowedID(),
                follow.getCreatedAt()
        );

        return Response.ok(followerResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "get-followers-cache")
    public Response getFollowers(@HeaderParam("X-User-Id") String followerID) {
        final List<Follow> followers = getFollowersHandler.handle(new GetFollowersQuery(followerID));
        final List<FollowerResponse> result = followers.stream()
                .map(response ->
                        new FollowerResponse(
                                response.getFollowerID(),
                                response.getFollowedID(),
                                response.getCreatedAt()
                        )
                ).toList();
        return Response.ok(result).build();
    }

}