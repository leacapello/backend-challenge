package ar.com.lcapello.uala.challenge.timeline.infrastructure.rest.dto;

import java.time.Instant;

public record TimelineResponse(String tweetId, String followedId, String message, Instant createdAt) {
}
