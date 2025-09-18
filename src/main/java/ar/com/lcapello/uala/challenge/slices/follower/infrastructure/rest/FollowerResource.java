package ar.com.lcapello.uala.challenge.slices.follower.infrastructure.rest;

import ar.com.lcapello.uala.challenge.slices.follower.application.command.AddFollowCommand;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.AddFollowCommandHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.RemoveFollowCommand;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.RemoveFollowCommandHandler;
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

@Path("/followers")
public class FollowerResource {

    @Inject
    AddFollowCommandHandler addFollowCommandHandler;

    @Inject
    RemoveFollowCommandHandler removeFollowCommandHandler;

    @Inject
    GetFollowingHandler getFollowingHandler;

    @POST
    @Path("/{followedID}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam("X-User-Id") String followerID,
                           @PathParam("followedID") String followedID) {

        Follow follow = addFollowCommandHandler.handle(new AddFollowCommand(followerID, followedID));
        FollowerResponse followerResponse = new FollowerResponse(
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
    public Response getFollowed(@HeaderParam("X-User-Id") String followerID,
                                @PathParam("followedID") String followedID) {

        FollowerResponse result = getFollowedData(followerID, followedID);
        return Response.ok(result).build();
    }

    @CacheResult(cacheName = "get-followed-cache")
    public FollowerResponse getFollowedData(String followerID, String followedID) {
        Follow follow = getFollowingHandler.handle(new GetFollowingQuery(followerID, followedID))
                .orElseThrow(() -> new EntityNotFoundException("Followed not found"));

        return new FollowerResponse(
                follow.getFollowerID(),
                follow.getFollowedID(),
                follow.getCreatedAt()
        );
    }

}