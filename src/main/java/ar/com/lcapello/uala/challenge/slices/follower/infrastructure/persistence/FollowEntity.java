package ar.com.lcapello.uala.challenge.slices.follower.infrastructure.persistence;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@IdClass(FollowEntity.FollowId.class)
@Table(name = "Followers")
public class FollowEntity {

    @Id
    @Column(nullable = false)
    private String followerID;

    @Id
    @Column(nullable = false)
    private String followedID;

    @Column(nullable = false)
    private Instant createdAt;

    public String getFollowerID() {
        return followerID;
    }

    public void setFollowerID(String followerID) {
        this.followerID = followerID;
    }

    public String getFollowedID() {
        return followedID;
    }

    public void setFollowedID(String followedID) {
        this.followedID = followedID;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static class FollowId implements Serializable {
        private String followerID;
        private String followedID;

        public FollowId() {}

        public FollowId(String followerID, String followedID) {
            this.followerID = followerID;
            this.followedID = followedID;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FollowId followId = (FollowId) o;
            return Objects.equals(followerID, followId.followerID) && Objects.equals(followedID, followId.followedID);
        }

        @Override
        public int hashCode() {
            return Objects.hash(followerID, followedID);
        }
    }
}


