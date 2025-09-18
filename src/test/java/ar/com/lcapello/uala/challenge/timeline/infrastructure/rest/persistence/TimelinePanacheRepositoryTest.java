package ar.com.lcapello.uala.challenge.timeline.infrastructure.rest.persistence;

import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.persistence.TimelineEntity;
import ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.persistence.TimelinePanacheRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TimelinePanacheRepositoryTest {

    @Inject
    TimelinePanacheRepository repository;

    @Transactional
    void persistTimeline(String tweetId, String authorId, String followerId, String message, Instant createdAt) {
        TimelineEntity entity = new TimelineEntity();
        entity.setTweetId(tweetId);
        entity.setAuthorId(authorId);
        entity.setFollowerId(followerId);
        entity.setMessage(message);
        entity.setCreatedAt(createdAt);
        repository.persist(entity);
    }

    @Test
    public void testFindByFollowerWithPagingAndOrdering() {
        // given: persistimos 3 timelines del mismo follower en distintos momentos
        String follower = "user-123";
        persistTimeline("t1", "author-1", follower, "msg1", Instant.parse("2023-01-01T10:00:00Z"));
        persistTimeline("t2", "author-2", follower, "msg2", Instant.parse("2023-01-01T12:00:00Z"));
        persistTimeline("t3", "author-3", follower, "msg3", Instant.parse("2023-01-01T14:00:00Z"));

        // y otro de otro follower (no debería aparecer en el resultado)
        persistTimeline("tX", "author-X", "other-user", "msgX", Instant.now());

        // when: pedimos solo los 2 primeros (pageSize=2) de la página 1
        List<Timeline> resultPage1 = repository.findByFollower(follower, 1, 2);

        // then: debe devolver los más nuevos primero (t3, t2)
        assertEquals(2, resultPage1.size());
        assertEquals("t3", resultPage1.get(0).getTweetId());
        assertEquals("t2", resultPage1.get(1).getTweetId());

        // when: pedimos página 2 (el tercero)
        List<Timeline> resultPage2 = repository.findByFollower(follower, 2, 2);

        // then: solo queda el t1
        assertEquals(1, resultPage2.size());
        assertEquals("t1", resultPage2.get(0).getTweetId());
    }

    @Test
    public void testFindByFollowerReturnsEmptyWhenNoMatches() {
        List<Timeline> result = repository.findByFollower("no-existe", 1, 10);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSaveAllPersistsTimelines() {
        String follower = "user-batch";

        List<Timeline> timelines = List.of(
                new Timeline("bt1", "author-1", follower, "msg1", Instant.parse("2023-01-02T10:00:00Z")),
                new Timeline("bt2", "author-2", follower, "msg2", Instant.parse("2023-01-02T11:00:00Z")),
                new Timeline("bt3", "author-3", follower, "msg3", Instant.parse("2023-01-02T12:00:00Z"))
        );

        repository.saveAll(timelines);

        List<Timeline> result = repository.findByFollower(follower, 1, 10);

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getTweetId().equals("bt1")));
        assertTrue(result.stream().anyMatch(t -> t.getTweetId().equals("bt2")));
        assertTrue(result.stream().anyMatch(t -> t.getTweetId().equals("bt3")));
    }
}