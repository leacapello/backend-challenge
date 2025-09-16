package ar.com.lcapello.uala.challenge.follower.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class FollowPanacheRepositoryTest {

    @Inject
    FollowPanacheRepository repository;

    @Test
    public void testSaveAndFind() {
        // given
        String suffix = String.valueOf(System.currentTimeMillis());
        Follow follow = new Follow(
                new UserID("f1-" + suffix),
                new UserID("f2-" + suffix),
                Instant.now()
        );

        // when
        repository.save(follow);

        // then - find pair
        Optional<Follow> found = repository.find(new UserID("f1-" + suffix), new UserID("f2-" + suffix));
        assertTrue(found.isPresent());
        assertEquals("f1-" + suffix, found.get().getFollowerID().value());
        assertEquals("f2-" + suffix, found.get().getFollowedID().value());
        assertNotNull(found.get().getCreatedAt());

        // then - findFollowers
        List<UserID> followers = repository.findFollowers(new UserID("f2-" + suffix));
        assertTrue(followers.stream().map(UserID::value).anyMatch(id -> id.equals("f1-" + suffix)));

        // then - findFollowing
        List<UserID> following = repository.findFollowing(new UserID("f1-" + suffix));
        assertTrue(following.stream().map(UserID::value).anyMatch(id -> id.equals("f2-" + suffix)));
    }

    @Test
    public void testDelete() {
        // given
        String suffix = String.valueOf(System.currentTimeMillis());
        Follow follow = new Follow(
                new UserID("a1-" + suffix),
                new UserID("a2-" + suffix),
                Instant.now()
        );
        repository.save(follow);

        // when
        repository.delete(follow);

        // then
        Optional<Follow> found = repository.find(new UserID("a1-" + suffix), new UserID("a2-" + suffix));
        assertTrue(found.isEmpty());
    }

    @Test
    public void testFindFollowersReturnsMultiple() {
        // given: u2 tiene dos seguidores u1 y u3
        String suffix = String.valueOf(System.currentTimeMillis());
        String u1 = "u1-" + suffix;
        String u2 = "u2-" + suffix;
        String u3 = "u3-" + suffix;
        repository.save(new Follow(new UserID(u1), new UserID(u2), Instant.now()));
        repository.save(new Follow(new UserID(u3), new UserID(u2), Instant.now()));

        // when
        List<UserID> followers = repository.findFollowers(new UserID(u2));

        // then
        List<String> values = followers.stream().map(UserID::value).toList();
        assertTrue(values.contains(u1));
        assertTrue(values.contains(u3));
        assertEquals(2, values.size());
    }

    @Test
    public void testFindFollowingReturnsMultiple() {
        // given: u1 sigue a u2 y u3
        String suffix = String.valueOf(System.currentTimeMillis());
        String u1 = "u1-" + suffix;
        String u2 = "u2-" + suffix;
        String u3 = "u3-" + suffix;
        repository.save(new Follow(new UserID(u1), new UserID(u2), Instant.now()));
        repository.save(new Follow(new UserID(u1), new UserID(u3), Instant.now()));

        // when
        List<UserID> following = repository.findFollowing(new UserID(u1));

        // then
        List<String> values = following.stream().map(UserID::value).toList();
        assertTrue(values.contains(u2));
        assertTrue(values.contains(u3));
        assertEquals(2, values.size());
    }

    @Test
    public void testFindFollowersReturnsEmptyList() {
        // when
        String suffix = String.valueOf(System.currentTimeMillis());
        List<UserID> followers = repository.findFollowers(new UserID("nobody-" + suffix));
        // then
        assertNotNull(followers);
        assertTrue(followers.isEmpty());
    }

    @Test
    public void testFindFollowingReturnsEmptyList() {
        // when
        String suffix = String.valueOf(System.currentTimeMillis());
        List<UserID> following = repository.findFollowing(new UserID("lonely-" + suffix));
        // then
        assertNotNull(following);
        assertTrue(following.isEmpty());
    }
}
