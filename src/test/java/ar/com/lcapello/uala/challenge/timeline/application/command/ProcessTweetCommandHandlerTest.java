package ar.com.lcapello.uala.challenge.timeline.application.command;

import ar.com.lcapello.uala.challenge.slices.timeline.application.command.ProcessTweetCommand;
import ar.com.lcapello.uala.challenge.slices.timeline.application.command.ProcessTweetCommandHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.FollowersReader;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineCommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProcessTweetCommandHandlerTest {

    private FollowersReader followersReader;
    private TimelineCommandRepository repository;
    private ProcessTweetCommandHandler handler;

    @BeforeEach
    void setUp() {
        followersReader = mock(FollowersReader.class);
        repository = mock(TimelineCommandRepository.class);
        handler = new ProcessTweetCommandHandler(followersReader, repository);
    }

    @Test
    void handle_shouldCreateTimelineEntriesForEachFollower() {
        ProcessTweetCommand event = new ProcessTweetCommand(
                "tweet-123",
                "author-1",
                "hola mundo",
                Instant.now()
        );

        when(followersReader.findFollowersOf("author-1"))
                .thenReturn(List.of("user-1", "user-2"));

        handler.handle(event);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Timeline>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(captor.capture());

        List<Timeline> saved = captor.getValue();
        assertEquals(2, saved.size());

        for (Timeline t : saved) {
            assertEquals("tweet-123", t.getTweetId());
            assertEquals("author-1", t.getAuthorId());
            assertEquals("hola mundo", t.getMessage());
            assertNotNull(t.getCreatedAt());
            assertTrue(List.of("user-1","user-2").contains(t.getFollowerId()));
        }
    }

    @Test
    void handle_shouldWorkWithNoFollowers() {
        ProcessTweetCommand event = new ProcessTweetCommand(
                "tweet-456",
                "author-2",
                "sin seguidores",
                Instant.now()
        );

        when(followersReader.findFollowersOf("author-2"))
                .thenReturn(List.of());

        handler.handle(event);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Timeline>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(captor.capture());

        List<Timeline> saved = captor.getValue();
        assertTrue(saved.isEmpty());
    }
}