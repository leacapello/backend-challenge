package ar.com.lcapello.uala.challenge.timeline.application.query;

import ar.com.lcapello.uala.challenge.timeline.application.config.TimelineQueryConfig;
import ar.com.lcapello.uala.challenge.timeline.application.exception.InvalidPageSizeException;
import ar.com.lcapello.uala.challenge.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.TimelineQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetTimelineByFollowerHandlerTest {

    private TimelineQueryRepository repository;
    private TimelineQueryConfig config;
    private GetTimelineByFollowerHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(TimelineQueryRepository.class);
        config = mock(TimelineQueryConfig.class);
        handler = new GetTimelineByFollowerHandler(repository, config);
    }

    @Test
    void handle_shouldReturnTimelineList() {
        GetTimelineByFollowerQuery query = new GetTimelineByFollowerQuery("user-123", 0, 10);

        when(config.maxPageSize()).thenReturn(50);
        List<Timeline> expected = List.of(mock(Timeline.class), mock(Timeline.class));
        when(repository.findByFollower("user-123", 0, 10)).thenReturn(expected);

        List<Timeline> result = handler.handle(query);

        assertEquals(2, result.size());
        verify(repository).findByFollower("user-123", 0, 10);
    }

    @Test
    void handle_shouldThrowWhenPageSizeExceedsMax() {
        GetTimelineByFollowerQuery query = new GetTimelineByFollowerQuery("user-123", 0, 100);

        when(config.maxPageSize()).thenReturn(50);

        InvalidPageSizeException ex = assertThrows(
                InvalidPageSizeException.class,
                () -> handler.handle(query)
        );

        assertTrue(ex.getMessage().contains("less than or equal to 50"));
        verify(repository, never()).findByFollower(any(), anyInt(), anyInt());
    }

}