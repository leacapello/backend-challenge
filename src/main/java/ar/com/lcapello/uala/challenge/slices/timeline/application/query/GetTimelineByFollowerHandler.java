package ar.com.lcapello.uala.challenge.slices.timeline.application.query;

import ar.com.lcapello.uala.challenge.slices.timeline.application.config.TimelineQueryConfig;
import ar.com.lcapello.uala.challenge.slices.timeline.application.exception.InvalidPageSizeException;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineQueryRepository;
import java.util.List;

public class GetTimelineByFollowerHandler {

    private final TimelineQueryRepository repository;
    private final TimelineQueryConfig config;

    public GetTimelineByFollowerHandler(TimelineQueryRepository timelineQueryRepository,
                                        TimelineQueryConfig timelineQueryConfig) {
        this.repository = timelineQueryRepository;
        this.config = timelineQueryConfig;
    }

    public List<Timeline> handle(GetTimelineByFollowerQuery query) {
        if (query.pageSize() > config.maxPageSize()) {
            throw new InvalidPageSizeException("pageSize must be less than or equal to " + config.maxPageSize());
        }

        return repository.findByFollower(query.followerId(), query.page(), query.pageSize());
    }

}