package ar.com.lcapello.uala.challenge.follower.domain.model;

import ar.com.lcapello.uala.challenge.follower.domain.exception.InvalidFollowException;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;

import java.time.Instant;
import java.util.Objects;

public class Follow {

    private final UserID followerID;
    private final UserID followedID;
    private final Instant createdAt;

    public Follow(UserID followerID, UserID followedID, Instant createdAt) {
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

    public UserID getFollowerID() {
        return followerID;
    }

    public UserID getFollowedID() {
        return followedID;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}