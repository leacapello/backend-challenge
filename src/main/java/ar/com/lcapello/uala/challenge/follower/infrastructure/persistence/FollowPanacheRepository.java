package ar.com.lcapello.uala.challenge.follower.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowCommandRepository;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.FollowerReader;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FollowPanacheRepository implements PanacheRepositoryBase<FollowEntity, FollowEntity.FollowId>, FollowCommandRepository, FollowQueryRepository, FollowerReader {

    @Override
    @Transactional
    public void save(Follow follow) {
        final FollowEntity entity = new FollowEntity();
        entity.setFollowerID(follow.getFollowerID());
        entity.setFollowedID(follow.getFollowedID());
        entity.setCreatedAt(follow.getCreatedAt());
        persist(entity);
    }

    @Override
    @Transactional
    public void delete(Follow follow) {
        deleteById(new FollowEntity.FollowId(follow.getFollowerID(), follow.getFollowedID()));
    }

    @Override
    public Optional<Follow> find(String followerID, String followedID) {
        return findByIdOptional(new FollowEntity.FollowId(followerID, followedID))
                .map(entity -> new Follow(entity.getFollowerID(), entity.getFollowedID(), entity.getCreatedAt()));
    }

    @Override
    public List<Follow> findFollowers(String followedId) {
        return find("SELECT f FROM FollowEntity f WHERE f.followedID = :followedId",
                Parameters.with("followedId", followedId))
                .stream()
                .map(entity -> new Follow(
                        entity.getFollowerID(),
                        entity.getFollowedID(),
                        entity.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public List<String> findFollowersOf(String followedID) {
        return find("select f.followerID from FollowEntity f where f.followedID = ?1", followedID)
                .stream()
                .map(String::valueOf)
                .toList();
    }

}