package ar.com.lcapello.uala.challenge.follower.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowCommandRepository;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FollowPanacheRepository implements PanacheRepositoryBase<FollowEntity, FollowEntity.FollowId>, FollowCommandRepository, FollowQueryRepository {

    @Override
    @Transactional
    public void save(Follow follow) {
        final FollowEntity entity = new FollowEntity();
        entity.setFollowerID(follow.getFollowerID().value());
        entity.setFollowedID(follow.getFollowedID().value());
        entity.setCreatedAt(follow.getCreatedAt());
        persist(entity);
    }

    @Override
    @Transactional
    public void delete(Follow follow) {
        deleteById(new FollowEntity.FollowId(follow.getFollowerID().value(), follow.getFollowedID().value()));
    }

    @Override
    public Optional<Follow> find(UserID followerID, UserID followedID) {
        return findByIdOptional(new FollowEntity.FollowId(followerID.value(), followedID.value()))
                .map(entity -> new Follow(new UserID(entity.getFollowerID()), new UserID(entity.getFollowedID()), entity.getCreatedAt()));
    }

    @Override
    public List<UserID> findFollowers(UserID userID) {
        return find("SELECT f.followerID FROM FollowEntity f WHERE f.followedID = ?1", userID.value())
                .project(String.class)
                .list()
                .stream()
                .map(UserID::new)
                .toList();
    }

    @Override
    public List<UserID> findFollowing(UserID userID) {
        return find("SELECT f.followedID FROM FollowEntity f WHERE f.followerID = ?1", userID.value())
                .project(String.class)
                .list()
                .stream()
                .map(UserID::new)
                .toList();
    }

}