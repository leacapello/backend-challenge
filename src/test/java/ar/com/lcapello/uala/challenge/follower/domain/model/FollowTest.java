package ar.com.lcapello.uala.challenge.follower.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import ar.com.lcapello.uala.challenge.follower.domain.exception.InvalidFollowException;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import org.junit.jupiter.api.Test;
import java.time.Instant;

public class FollowTest {

    @Test
    void shouldCreateFollowWhenValidData() {
        UserID follower = new UserID("user1");
        UserID followed = new UserID("user2");
        Instant now = Instant.now();

        Follow follow = new Follow(follower, followed, now);

        assertEquals(follower, follow.getFollowerID());
        assertEquals(followed, follow.getFollowedID());
        assertEquals(now, follow.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenFollowerEqualsFollowed() {
        UserID user = new UserID("user1");
        Instant now = Instant.now();

        InvalidFollowException ex = assertThrows(
                InvalidFollowException.class,
                () -> new Follow(user, user, now)
        );

        assertEquals("A user cannot follow themselves", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreatedAtIsNull() {
        UserID follower = new UserID("user1");
        UserID followed = new UserID("user2");

        InvalidFollowException ex = assertThrows(
                InvalidFollowException.class,
                () -> new Follow(follower, followed, null)
        );

        assertEquals("createdAt cannot be null", ex.getMessage());
    }

}