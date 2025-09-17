package ar.com.lcapello.uala.challenge.timeline.application.command;

import java.time.Instant;

public record CreateTweetEvent(
        String tweetId,
        String authorId,
        String message,
        Instant createdAt
) {
}
