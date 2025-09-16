package ar.com.lcapello.uala.challenge.follower.application.query;

import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetFollowersHandlerTest {

    @Test
    void shouldReturnFollowersWhenFound() {
        // given
        FollowQueryRepository repository = mock(FollowQueryRepository.class);
        GetFollowersHandler handler = new GetFollowersHandler(repository);

        List<UserID> followers = List.of(new UserID("u1"), new UserID("u2"));
        when(repository.findFollowers(new UserID("target"))).thenReturn(followers);

        // when
        List<UserID> result = handler.handle(new GetFollowersQuery("target"));

        // then
        assertEquals(2, result.size());
        assertEquals("u1", result.get(0).value());
        assertEquals("u2", result.get(1).value());
        verify(repository, times(1)).findFollowers(new UserID("target"));
    }

    @Test
    void shouldReturnEmptyWhenNoFollowers() {
        // given
        FollowQueryRepository repository = mock(FollowQueryRepository.class);
        GetFollowersHandler handler = new GetFollowersHandler(repository);

        when(repository.findFollowers(new UserID("nobody"))).thenReturn(List.of());

        // when
        List<UserID> result = handler.handle(new GetFollowersQuery("nobody"));

        // then
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findFollowers(new UserID("nobody"));
    }
}
