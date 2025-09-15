package ar.com.lcapello.uala.challenge.tweet.domain.vo;

import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TweetIDTest {

    @Test
    void shouldCreateTweetIDWhenValueIsValid() {
        TweetID tweetID = new TweetID("12345");
        assertEquals("12345", tweetID.value());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        InvalidTweetIdException ex = assertThrows(
                InvalidTweetIdException.class,
                () -> new TweetID(null)
        );
        assertEquals("TweetId cannot be null or blank", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsBlank() {
        InvalidTweetIdException ex = assertThrows(
                InvalidTweetIdException.class,
                () -> new TweetID("   ")
        );
        assertEquals("TweetId cannot be null or blank", ex.getMessage());
    }

}