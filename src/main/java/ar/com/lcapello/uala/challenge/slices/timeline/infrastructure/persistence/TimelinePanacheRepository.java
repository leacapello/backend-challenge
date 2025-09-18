package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineCommandRepository;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineQueryRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TimelinePanacheRepository implements PanacheRepositoryBase<TimelineEntity, TimelineEntity.TimelineId>, TimelineQueryRepository, TimelineCommandRepository {

    @Override
    public List<Timeline> findByFollower(String followerId, Integer page, Integer pageSize) {
        final int safePage = (page > 0) ? page - 1 : 0;   //panache empieza con 0 la pagina

        return find("SELECT f FROM TimelineEntity f WHERE f.followerId = :followerId order by f.createdAt desc",
                Parameters.with("followerId", followerId))
                .page(Page.of(safePage, pageSize))
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