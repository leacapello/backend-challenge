package ar.com.lcapello.uala.challenge.slices.timeline.domain.repository;

import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import java.util.List;

public interface TimelineCommandRepository {
    void saveAll(List<Timeline> entries);
}
