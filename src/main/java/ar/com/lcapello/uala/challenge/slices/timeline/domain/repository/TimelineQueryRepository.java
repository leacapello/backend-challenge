package ar.com.lcapello.uala.challenge.slices.timeline.domain.repository;

import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;

import java.util.List;

public interface TimelineQueryRepository {
        List<Timeline> findByFollower(String followerId, Integer page, Integer pageSize);
}