package ar.com.lcapello.uala.challenge.timeline.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.TimelineCommandRepository;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.TimelineQueryRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TimelinePanacheRepository implements PanacheRepositoryBase<TimelineEntity, TimelineEntity.TimelineId>, TimelineQueryRepository, TimelineCommandRepository {

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

    @Override
    @Transactional
    public void saveAll(List<Timeline> timelineList) {
        final List<TimelineEntity> entities = timelineList.stream()
                .map(t -> {
                    TimelineEntity e = new TimelineEntity();
                    e.setTweetId(t.getTweetId());
                    e.setFollowerId(t.getFollowerId());
                    e.setAuthorId(t.getAuthorId());
                    e.setMessage(t.getMessage());
                    e.setCreatedAt(t.getCreatedAt());
                    return e;
                })
                .toList();

        persist(entities);
    }
}