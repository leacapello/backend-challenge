package ar.com.lcapello.uala.challenge.slices.timeline.application.query;

import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineQueryRepository;
import java.util.List;

public class GetTimelineByFollowerHandler {

    private final TimelineQueryRepository repository;

    public GetTimelineByFollowerHandler(TimelineQueryRepository timelineQueryRepository) {
        this.repository = timelineQueryRepository;
    }

    public List<Timeline> handle(GetTimelineByFollowerQuery query) {
        return repository.findByFollower(query.followerId(), query.page(), query.pageSize());
    }

}