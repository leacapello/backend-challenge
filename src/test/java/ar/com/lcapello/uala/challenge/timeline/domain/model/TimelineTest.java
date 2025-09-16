package ar.com.lcapello.uala.challenge.timeline.domain.model;

import ar.com.lcapello.uala.challenge.timeline.domain.exception.InvalidTimelineException;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

public class TimelineTest {

    @Test
    void shouldCreateTimelineWhenAllFieldsAreValid() {
        Instant now = Instant.now();
        Timeline timeline = new Timeline("t1", "a1", "f1", "Hello", now);

        assertEquals("t1", timeline.getTweetId());
        assertEquals("a1", timeline.getAuthorId());
        assertEquals("f1", timeline.getFollowerId());
        assertEquals("Hello", timeline.getMessage());
        assertEquals(now, timeline.getCreatedAt());
    }

    @Test
    void shouldThrowWhenTweetIdIsBlank() {
        assertThrows(InvalidTimelineException.class, () ->
                new Timeline(" ", "a1", "f1", "Hello", Instant.now()));
    }

    @Test
    void shouldThrowWhenAuthorIdIsBlank() {
        assertThrows(InvalidTimelineException.class, () ->
                new Timeline("t1", "", "f1", "Hello", Instant.now()));
    }

    @Test
    void shouldThrowWhenFollowerIdIsBlank() {
        assertThrows(InvalidTimelineException.class, () ->
                new Timeline("t1", "a1", null, "Hello", Instant.now()));
    }

    @Test
    void shouldThrowWhenMessageIsBlank() {
        assertThrows(InvalidTimelineException.class, () ->
                new Timeline("t1", "a1", "f1", " ", Instant.now()));
    }

    @Test
    void shouldThrowWhenCreatedAtIsNull() {
        assertThrows(InvalidTimelineException.class, () ->
                new Timeline("t1", "a1", "f1", "Hello", null));
    }
}