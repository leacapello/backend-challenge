package ar.com.lcapello.uala.challenge.slices.follower.application.command;

public record RemoveFollowCommand(
        String followerId,
        String followedId
) {}