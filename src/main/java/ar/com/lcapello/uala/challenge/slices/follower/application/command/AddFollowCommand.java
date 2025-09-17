package ar.com.lcapello.uala.challenge.slices.follower.application.command;

public record AddFollowCommand(
        String followerId,
        String followedId
) {}


