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
    public void testSaveFollow() {
        // given
        Follow follow = new Follow(
                new UserID("f1"),
                new UserID("f2"),
                Instant.now()
        );

        // when
        repository.save(follow);

        // then
        FollowEntity.FollowId id = new FollowEntity.FollowId("f1", "f2");
        FollowEntity entity = repository.findById(id);
        assertNotNull(entity);
        assertEquals("f1", entity.getFollowerID());
        assertEquals("f2", entity.getFollowedID());
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    public void testFindWhenExists() {
        // given
        Follow follow = new Follow(
                new UserID("a1"),
                new UserID("a2"),
                Instant.now()
        );
        repository.save(follow);

        // when
        Optional<Follow> result = repository.find(new UserID("a1"), new UserID("a2"));

        // then
        assertTrue(result.isPresent());
        Follow found = result.get();
        assertEquals("a1", found.getFollowerID().value());
        assertEquals("a2", found.getFollowedID().value());
        assertNotNull(found.getCreatedAt());
    }

    @Test
    public void testFindWhenNotExists() {
        // when
        Optional<Follow> result = repository.find(new UserID("nope"), new UserID("missing"));

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindFollowers() {
        // given
        repository.save(new Follow(new UserID("x1"), new UserID("target"), Instant.now()));
        repository.save(new Follow(new UserID("x2"), new UserID("target"), Instant.now()));
        repository.save(new Follow(new UserID("x3"), new UserID("other"), Instant.now()));

        // when
        List<Follow> followers = repository.findFollowers(new UserID("target"));

        // then
        assertEquals(2, followers.size());
        assertTrue(followers.stream().allMatch(f -> f.getFollowedID().value().equals("target")));
    }

    @Test
    public void testDeleteFollow() {
        // given
        Follow follow = new Follow(new UserID("d1"), new UserID("d2"), Instant.now());
        repository.save(follow);

        // when
        repository.delete(follow);

        // then
        Optional<Follow> result = repository.find(new UserID("d1"), new UserID("d2"));
        assertTrue(result.isEmpty());
    }
}