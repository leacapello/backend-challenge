package ar.com.lcapello.uala.challenge.tweet.domain.model;

import ar.com.lcapello.uala.challenge.common.domain.vo.UserID;
import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetException;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class TweetTest {

    @Test
    void shouldCreateTweetWhenValuesAreValid() {
        TweetID tweetID = new TweetID("t1");
        UserID userID = new UserID("u1");
        String message = "Mensaje válido";
        Instant createdAt = Instant.now();

        Tweet tweet = new Tweet(tweetID, userID, message, createdAt);

        assertEquals(tweetID, tweet.getTweetID());
        assertEquals(userID, tweet.getAuthorID());
        assertEquals(message, tweet.getMessage());
        assertEquals(createdAt, tweet.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNull() {
        TweetID tweetID = new TweetID("t1");
        UserID userID = new UserID("u1");
        Instant createdAt = Instant.now();

        InvalidTweetException ex = assertThrows(
                InvalidTweetException.class,
                () -> new Tweet(tweetID, userID, null, createdAt)
        );
        assertEquals("Message cannot be null or blank", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsBlank() {
        TweetID tweetID = new TweetID("t1");
        UserID userID = new UserID("u1");
        Instant createdAt = Instant.now();

        InvalidTweetException ex = assertThrows(
                InvalidTweetException.class,
                () -> new Tweet(tweetID, userID, "   ", createdAt)
        );
        assertEquals("Message cannot be null or blank", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageExceedsMaxLength() {
        TweetID tweetID = new TweetID("t1");
        UserID userID = new UserID("u1");
        String longMessage = "a".repeat(281);
        Instant createdAt = Instant.now();

        InvalidTweetException ex = assertThrows(
                InvalidTweetException.class,
                () -> new Tweet(tweetID, userID, longMessage, createdAt)
        );
        assertEquals("Message cannot exceed 280 characters", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreatedAtIsNull() {
        TweetID tweetID = new TweetID("t1");
        UserID userID = new UserID("u1");
        String message = "Mensaje válido";

        InvalidTweetException ex = assertThrows(
                InvalidTweetException.class,
                () -> new Tweet(tweetID, userID, message, null)
        );
        assertEquals("createdAt cannot be null", ex.getMessage());
    }

}