package ar.com.lcapello.uala.challenge.follower.application.query;

public record GetFollowingQuery(
        String followerID,
        String followedID
) {}
