package ar.com.lcapello.uala.challenge.slices.follower.domain.repository;

import ar.com.lcapello.uala.challenge.slices.follower.domain.model.Follow;
import java.util.List;
import java.util.Optional;

public interface FollowQueryRepository {
    Optional<Follow> find(String followerID, String followedID);
}