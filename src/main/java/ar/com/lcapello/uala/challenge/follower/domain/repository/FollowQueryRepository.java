package ar.com.lcapello.uala.challenge.follower.domain.repository;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;

import java.util.List;
import java.util.Optional;

public interface FollowQueryRepository {
    Optional<Follow> find(UserID followerID, UserID followedID);
    List<UserID> findFollowers(UserID userID);
    List<UserID> findFollowing(UserID userID);
}


