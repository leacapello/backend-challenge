package ar.com.lcapello.uala.challenge.tweet.application.command;

import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetException;
import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetIdException;
import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetCommandRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateTweetCommandHandlerTest {

    private TweetCommandRepository repository;
    private TweetEventPublisher publisher;
    private CreateTweetCommandHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(TweetCommandRepository.class);
        publisher = mock(TweetEventPublisher.class);
        handler = new CreateTweetCommandHandler(repository, publisher);
    }

    @Test
    void handle_shouldCreateSaveAndPublishTweet() {
        CreateTweetCommand command = new CreateTweetCommand(
                "123e4567-e89b-12d3-a456-426614174000",
                "user-1",
                "Hello world"
        );

        handler.handle(command);

        ArgumentCaptor<Tweet> captor = ArgumentCaptor.forClass(Tweet.class);
        verify(repository).save(captor.capture());
        verify(publisher).publish(captor.getValue());

        Tweet saved = captor.getValue();
        assertEquals("123e4567-e89b-12d3-a456-426614174000", saved.getTweetID().value());
        assertEquals("user-1", saved.getAuthorID());
        assertEquals("Hello world", saved.getMessage());
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void handle_shouldThrowIfTweetIdInvalid() {
        CreateTweetCommand command = new CreateTweetCommand(
                "",  // inválido
                "user-1",
                "Hello world"
        );

        assertThrows(InvalidTweetIdException.class, () -> handler.handle(command));
        verify(repository, never()).save(any());
        verify(publisher, never()).publish(any());
    }

    @Test
    void handle_shouldThrowIfMessageInvalid() {
        CreateTweetCommand command = new CreateTweetCommand(
                "123e4567-e89b-12d3-a456-426614174000",
                "user-1",
                ""  // inválido
        );

        assertThrows(InvalidTweetException.class, () -> handler.handle(command));
        verify(repository, never()).save(any());
        verify(publisher, never()).publish(any());
    }
}