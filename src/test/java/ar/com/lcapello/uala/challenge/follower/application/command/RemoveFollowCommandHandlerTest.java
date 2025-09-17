package ar.com.lcapello.uala.challenge.follower.application.command;

import ar.com.lcapello.uala.challenge.follower.domain.exception.InvalidFollowException;
import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowCommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RemoveFollowCommandHandlerTest {

    private FollowCommandRepository repository;
    private RemoveFollowCommandHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(FollowCommandRepository.class);
        handler = new RemoveFollowCommandHandler(repository);
    }

    @Test
    void handle_shouldDeleteFollow() {
        RemoveFollowCommand command = new RemoveFollowCommand(
                "user-1",
                "user-2"
        );

        handler.handle(command);

        ArgumentCaptor<Follow> captor = ArgumentCaptor.forClass(Follow.class);
        verify(repository).delete(captor.capture());

        Follow removed = captor.getValue();
        assertEquals("user-1", removed.getFollowerID());
        assertEquals("user-2", removed.getFollowedID());
        assertNotNull(removed.getCreatedAt());
    }

    @Test
    void handle_shouldThrowIfSameUser() {
        RemoveFollowCommand command = new RemoveFollowCommand(
                "user-1",
                "user-1" // mismo usuario
        );

        assertThrows(InvalidFollowException.class, () -> handler.handle(command));
        verify(repository, never()).delete(any());
    }
}
