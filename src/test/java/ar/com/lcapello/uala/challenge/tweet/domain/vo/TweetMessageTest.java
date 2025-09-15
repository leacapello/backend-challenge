package ar.com.lcapello.uala.challenge.tweet.domain.vo;

import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetMessageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TweetMessageTest {

    @Test
    void shouldCreateTweetMessageWhenValueIsValid() {
        String validMessage = "Este es un tweet válido";
        TweetMessage tweetMessage = new TweetMessage(validMessage);

        assertEquals(validMessage, tweetMessage.message());
        assertTrue(tweetMessage.toString().contains(validMessage)); // cubre toString()
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNull() {
        InvalidTweetMessageException ex = assertThrows(
                InvalidTweetMessageException.class,
                () -> new TweetMessage(null)
        );
        assertEquals("Message cannot be null or blank", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsBlank() {
        InvalidTweetMessageException ex = assertThrows(
                InvalidTweetMessageException.class,
                () -> new TweetMessage("   ")
        );
        assertEquals("Message cannot be null or blank", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageExceedsMaxLength() {
        String longMessage = "a".repeat(281); // genera un string de 281 caracteres
        InvalidTweetMessageException ex = assertThrows(
                InvalidTweetMessageException.class,
                () -> new TweetMessage(longMessage)
        );
        assertEquals("Message cannot exceed 280 characters", ex.getMessage());
    }
}