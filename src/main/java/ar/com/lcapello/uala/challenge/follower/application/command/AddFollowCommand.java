package ar.com.lcapello.uala.challenge.follower.application.command;

public record AddFollowCommand(
        String followerID,
        String followedID
) {}


