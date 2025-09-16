package ar.com.lcapello.uala.challenge.timeline.infrastructure.rest.persistence;

import ar.com.lcapello.uala.challenge.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.TimelineQueryRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TimelinePanacheRepository implements PanacheRepositoryBase<TimelineEntity, TimelineEntity.TimelineId>, TimelineQueryRepository {

    @Override
    public List<Timeline> findByFollower(String followerId, Integer page, Integer pageSize) {
        return find("followerId = ?1 order by createdAt desc", followerId)
                .page(Page.of(page, pageSize))
                .list()
                .stream()
                .map(entity -> new Timeline(
                        entity.getTweetId(),
                        entity.getAuthorId(),
                        entity.getFollowerId(),
                        entity.getMessage(),
                        entity.getCreatedAt()
                ))
                .toList();
    }
}