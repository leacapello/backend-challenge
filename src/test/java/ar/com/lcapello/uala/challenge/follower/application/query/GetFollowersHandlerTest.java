package ar.com.lcapello.uala.challenge.follower.application.query;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetFollowersHandlerTest {

    private FollowQueryRepository repository;
    private GetFollowersHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(FollowQueryRepository.class);
        handler = new GetFollowersHandler(repository);
    }

    @Test
    void shouldReturnFollowersFromRepository() {
        // Arrange
        String targetUserId = "user123";
        GetFollowersQuery query = new GetFollowersQuery(targetUserId);

        Follow follow1 = new Follow(new UserID("f1"), new UserID(targetUserId), Instant.now());
        Follow follow2 = new Follow(new UserID("f2"), new UserID(targetUserId), Instant.now());

        when(repository.findFollowers(any(UserID.class)))
                .thenReturn(List.of(follow1, follow2));

        // Act
        List<Follow> result = handler.handle(query);

        // Assert
        assertEquals(2, result.size());
        assertEquals(List.of(follow1, follow2), result);
        verify(repository, times(1)).findFollowers(new UserID(targetUserId));
    }
}