package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.persistence;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@IdClass(TimelineEntity.TimelineId.class)
@Table(name = "Timeline", indexes = {
    @Index(name = "idx_timeline_follower_created", columnList = "followerId, createdAt DESC"),
    @Index(name = "idx_timeline_author", columnList = "authorId")
})
public class TimelineEntity {

    @Id
    @Column(nullable = false)
    private String tweetId;

    @Id
    @Column(nullable = false)
    private String followerId;

    @Column(nullable = false)
    private String authorId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Instant createdAt;

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorID) {
        this.authorId = authorID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static class TimelineId implements Serializable {

        private String tweetId;
        private String followerId;

        public TimelineId() {
        }

        public TimelineId(String tweetId, String followerId) {
            this.tweetId = tweetId;
            this.followerId = followerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TimelineEntity.TimelineId timelineId = (TimelineEntity.TimelineId) o;
            return Objects.equals(tweetId, timelineId.tweetId) && Objects.equals(followerId, timelineId.followerId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tweetId, followerId);
        }
    }

}