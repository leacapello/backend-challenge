package ar.com.lcapello.uala.challenge.tweet.application.command;

import ar.com.lcapello.uala.challenge.user.domain.exception.InvalidUserIdException;
import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetException;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetCommandRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetIdException;
import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateTweetCommandHandlerTest {

    private TweetCommandRepository repository;
    private CreateTweetCommandHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(TweetCommandRepository.class);
        handler = new CreateTweetCommandHandler(repository);
    }

    @Test
    void handle_shouldCreateAndSaveTweet() {
        CreateTweetCommand command = new CreateTweetCommand(
                "123e4567-e89b-12d3-a456-426614174000",
                "user-1",
                "Hello world"
        );

        handler.handle(command);

        ArgumentCaptor<Tweet> captor = ArgumentCaptor.forClass(Tweet.class);
        verify(repository).save(captor.capture());

        Tweet saved = captor.getValue();
        assertEquals("123e4567-e89b-12d3-a456-426614174000", saved.getTweetID().value());
        assertEquals("user-1", saved.getAuthorID().value());
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
    }

    @Test
    void handle_shouldThrowIfUserIdInvalid() {
        CreateTweetCommand command = new CreateTweetCommand(
                "123e4567-e89b-12d3-a456-426614174000",
                "",  // inválido
                "Hello world"
        );

        assertThrows(InvalidUserIdException.class, () -> handler.handle(command));
        verify(repository, never()).save(any());
    }

    @Test
    void handle_shouldThrowIfMessageIdInvalid() {
        CreateTweetCommand command = new CreateTweetCommand(
                "123e4567-e89b-12d3-a456-426614174000",
                "user-1",
                ""  // inválido
        );

        assertThrows(InvalidTweetException.class, () -> handler.handle(command));
        verify(repository, never()).save(any());
    }

}