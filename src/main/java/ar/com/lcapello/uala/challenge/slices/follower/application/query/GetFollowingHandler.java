package ar.com.lcapello.uala.challenge.slices.follower.application.query;

import ar.com.lcapello.uala.challenge.slices.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.slices.follower.domain.repository.FollowQueryRepository;
import java.util.Optional;

public class GetFollowingHandler {

    private final FollowQueryRepository repository;

    public GetFollowingHandler(FollowQueryRepository repository) {
        this.repository = repository;
    }

    public Optional<Follow> handle(GetFollowingQuery query) {
        return repository.find(query.followerId(), query.followedId());
    }

}