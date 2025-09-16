package ar.com.lcapello.uala.challenge.tweet.application.query;

import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetQueryRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetTweetByIdHandlerTest {

    @Test
    void shouldReturnTweetWhenFound() {
        // given
        TweetQueryRepository repository = mock(TweetQueryRepository.class);
        GetTweetByIdHandler handler = new GetTweetByIdHandler(repository);

        Tweet tweet = new Tweet(
                new TweetID("abc123"),
                new UserID("user-123"),
                "Hola mundo",
                Instant.parse("2025-09-15T20:00:00Z")
        );

        when(repository.findById(new TweetID("abc123"))).thenReturn(Optional.of(tweet));

        // when
        Optional<Tweet> result = handler.handle(new GetTweetByIdQuery("abc123"));

        // then
        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getTweetID().value());
        assertEquals("Hola mundo", result.get().getMessage());
        verify(repository, times(1)).findById(new TweetID("abc123"));
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        // given
        TweetQueryRepository repository = mock(TweetQueryRepository.class);
        GetTweetByIdHandler handler = new GetTweetByIdHandler(repository);

        when(repository.findById(new TweetID("notfound"))).thenReturn(Optional.empty());

        // when
        Optional<Tweet> result = handler.handle(new GetTweetByIdQuery("notfound"));

        // then
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findById(new TweetID("notfound"));
    }
}