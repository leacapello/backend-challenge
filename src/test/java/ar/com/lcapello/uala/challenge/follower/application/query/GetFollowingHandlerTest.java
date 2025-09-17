package ar.com.lcapello.uala.challenge.follower.application.query;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetFollowingHandlerTest {

    private FollowQueryRepository repository;
    private GetFollowingHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(FollowQueryRepository.class);
        handler = new GetFollowingHandler(repository);
    }

    @Test
    void shouldReturnFollowFromRepository() {
        // Arrange
        String follower = "user123";
        String followed = "user456";
        GetFollowingQuery query = new GetFollowingQuery(follower, followed);

        Follow follow = new Follow(follower, followed, Instant.now());
        when(repository.find(any(String.class), any(String.class)))
                .thenReturn(Optional.of(follow));

        // Act
        Optional<Follow> result = handler.handle(query);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(follow, result.get());
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        // Arrange
        String follower = "user123";
        String followed = "user456";
        GetFollowingQuery query = new GetFollowingQuery(follower, followed);

        when(repository.find(any(String.class), any(String.class)))
                .thenReturn(Optional.empty());

        // Act
        Optional<Follow> result = handler.handle(query);

        // Assert
        assertTrue(result.isEmpty());
    }
}