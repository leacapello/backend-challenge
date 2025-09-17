package ar.com.lcapello.uala.challenge.slices.timeline.domain.model;

import ar.com.lcapello.uala.challenge.slices.timeline.domain.exception.InvalidTimelineException;

import java.time.Instant;

public class Timeline {

    private final String tweetId;
    private final String authorId;
    private final String followerId;
    private final String message;
    private final Instant createdAt;

    public Timeline(String tweetId, String authorId, String followerId, String message, Instant createdAt) {
        if (isBlank(tweetId) || isBlank(authorId) || isBlank(followerId) || isBlank(message) || createdAt == null) {
            throw new InvalidTimelineException("Timeline entry has null or empty fields");
        }
        this.tweetId = tweetId;
        this.authorId = authorId;
        this.followerId = followerId;
        this.message = message;
        this.createdAt = createdAt;
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    public String getTweetId() {
        return tweetId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}