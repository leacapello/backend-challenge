package ar.com.lcapello.uala.challenge.slices.follower.domain.model;

import ar.com.lcapello.uala.challenge.slices.follower.domain.exception.InvalidFollowException;
import java.time.Instant;
import java.util.Objects;

public class Follow {

    private final String followerID;
    private final String followedID;
    private final Instant createdAt;

    public Follow(String followerID, String followedID, Instant createdAt) {
        if (Objects.equals(followerID, followedID)) {
            throw new InvalidFollowException("A user cannot follow themselves");
        }
        if (createdAt == null) {
            throw new InvalidFollowException("createdAt cannot be null");
        }
        this.followerID = followerID;
        this.followedID = followedID;
        this.createdAt = createdAt;
    }

    public String getFollowerID() {
        return followerID;
    }

    public String getFollowedID() {
        return followedID;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}