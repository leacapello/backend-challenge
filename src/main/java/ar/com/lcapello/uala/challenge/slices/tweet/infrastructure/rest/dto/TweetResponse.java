package ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.rest.dto;

import java.time.Instant;

public record TweetResponse(
        String tweetID,
        String authorID,
        String message,
        Instant createdAt
) {
}
