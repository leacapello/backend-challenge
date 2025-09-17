package ar.com.lcapello.uala.challenge.slices.follower.infrastructure.rest.dto;

import java.time.Instant;

public record FollowerResponse(
        String followerId,
        String followedId,
        Instant createdAt
) {
}