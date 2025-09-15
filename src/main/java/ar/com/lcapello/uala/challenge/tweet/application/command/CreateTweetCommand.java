package ar.com.lcapello.uala.challenge.tweet.application.command;

public record CreateTweetCommand(
        String tweetID,
        String authorID,
        String message
) {}
