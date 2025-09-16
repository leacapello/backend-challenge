package ar.com.lcapello.uala.challenge.follower.application.query;

import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetFollowingHandlerTest {

    @Test
    void shouldReturnFollowingWhenFound() {
        // given
        FollowQueryRepository repository = mock(FollowQueryRepository.class);
        GetFollowingHandler handler = new GetFollowingHandler(repository);

        List<UserID> following = List.of(new UserID("u10"), new UserID("u20"));
        when(repository.findFollowing(new UserID("me"))).thenReturn(following);

        // when
        List<UserID> result = handler.handle(new GetFollowingQuery("me"));

        // then
        assertEquals(2, result.size());
        assertEquals("u10", result.get(0).value());
        assertEquals("u20", result.get(1).value());
        verify(repository, times(1)).findFollowing(new UserID("me"));
    }

    @Test
    void shouldReturnEmptyWhenNoFollowing() {
        // given
        FollowQueryRepository repository = mock(FollowQueryRepository.class);
        GetFollowingHandler handler = new GetFollowingHandler(repository);

        when(repository.findFollowing(new UserID("lonely"))).thenReturn(List.of());

        // when
        List<UserID> result = handler.handle(new GetFollowingQuery("lonely"));

        // then
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findFollowing(new UserID("lonely"));
    }
}
