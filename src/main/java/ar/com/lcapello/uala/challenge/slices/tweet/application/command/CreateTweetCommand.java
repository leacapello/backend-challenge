package ar.com.lcapello.uala.challenge.slices.tweet.application.command;

public record CreateTweetCommand(
        String tweetID,
        String authorID,
        String message
) {}
