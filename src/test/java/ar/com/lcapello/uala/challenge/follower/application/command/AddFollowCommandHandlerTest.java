package ar.com.lcapello.uala.challenge.follower.application.command;

import ar.com.lcapello.uala.challenge.slices.follower.application.command.AddFollowCommand;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.AddFollowCommandHandler;
import ar.com.lcapello.uala.challenge.slices.follower.domain.exception.InvalidFollowException;
import ar.com.lcapello.uala.challenge.slices.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.slices.follower.domain.repository.FollowCommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddFollowCommandHandlerTest {

    private FollowCommandRepository repository;
    private AddFollowCommandHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(FollowCommandRepository.class);
        handler = new AddFollowCommandHandler(repository);
    }

    @Test
    void handle_shouldCreateAndSaveFollow() {
        AddFollowCommand command = new AddFollowCommand(
                "user-1",
                "user-2"
        );

        Follow follow = handler.handle(command);

        ArgumentCaptor<Follow> captor = ArgumentCaptor.forClass(Follow.class);
        verify(repository).save(captor.capture());

        Follow saved = captor.getValue();
        assertEquals("user-1", saved.getFollowerID());
        assertEquals("user-2", saved.getFollowedID());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(follow.getCreatedAt());
    }

    @Test
    void handle_shouldThrowIfSameUser() {
        AddFollowCommand command = new AddFollowCommand(
                "user-1",
                "user-1" // mismo usuario
        );

        assertThrows(InvalidFollowException.class, () -> handler.handle(command));
        verify(repository, never()).save(any());
    }
}
