package ar.com.lcapello.uala.challenge.follower.application.command;

public record RemoveFollowCommand(
        String followerId,
        String followedId
) {}