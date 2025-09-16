package ar.com.lcapello.uala.challenge.tweet.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TweetPanacheRepositoryTest {

    @Inject
    TweetPanacheRepository repository;

    @Test
    public void testSaveTweet() {
        // given
        Tweet tweet = new Tweet(
                new TweetID("abc123"),
                new UserID("user-id"),
                "Hola mundo",
                Instant.now()
        );

        // when
        repository.save(tweet);

        // then
        TweetEntity entity = repository.findById("abc123");
        assertNotNull(entity);
        assertEquals("abc123", entity.getTweetID());
        assertEquals("user-id", entity.getAuthorID());
        assertEquals("Hola mundo", entity.getMessage());
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    public void testFindByIdWhenExists() {
        // given
        Tweet tweet = new Tweet(
                new TweetID("xyz999"),
                new UserID("user-123"),
                "Mensaje de prueba",
                Instant.now()
        );
        repository.save(tweet);

        // when
        Optional<Tweet> result = repository.findById(new TweetID("xyz999"));

        // then
        assertTrue(result.isPresent());
        Tweet found = result.get();
        assertEquals("xyz999", found.getTweetID().value());
        assertEquals("user-123", found.getAuthorID().value());
        assertEquals("Mensaje de prueba", found.getMessage());
        assertNotNull(found.getCreatedAt());
    }

    @Test
    public void testFindByIdWhenNotExists() {
        // when
        Optional<Tweet> result = repository.findById(new TweetID("no-existe"));

        // then
        assertTrue(result.isEmpty());
    }

}