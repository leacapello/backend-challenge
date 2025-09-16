package ar.com.lcapello.uala.challenge.follower.application.command;

public record AddFollowCommand(
        String followerId,
        String followedId
) {}


