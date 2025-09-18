package ar.com.lcapello.uala.challenge.timeline.application.query;

import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerQuery;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetTimelineByFollowerHandlerTest {

    private TimelineQueryRepository repository;
    private GetTimelineByFollowerHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(TimelineQueryRepository.class);
        handler = new GetTimelineByFollowerHandler(repository);
    }

    @Test
    void handle_shouldReturnTimelineList() {
        GetTimelineByFollowerQuery query = new GetTimelineByFollowerQuery("user-123", 0, 10);

        List<Timeline> expected = List.of(mock(Timeline.class), mock(Timeline.class));
        when(repository.findByFollower("user-123", 0, 10)).thenReturn(expected);

        List<Timeline> result = handler.handle(query);

        assertEquals(2, result.size());
        verify(repository).findByFollower("user-123", 0, 10);
    }

}