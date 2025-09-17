package ar.com.lcapello.uala.challenge.slices.follower.application.query;

public record GetFollowingQuery(
        String followerId,
        String followedId
) {}
