package ar.com.lcapello.uala.challenge.follower.infrastructure.rest.dto;

import java.time.Instant;

public record FollowerResponse(
        String followerID,
        String followedID,
        Instant createdAt
) {
}