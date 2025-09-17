package ar.com.lcapello.uala.challenge.slices.follower.domain.repository;

import ar.com.lcapello.uala.challenge.slices.follower.domain.model.Follow;

public interface FollowCommandRepository {
    void save(Follow follow);
    void delete(Follow follow);
}


