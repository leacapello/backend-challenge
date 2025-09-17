package ar.com.lcapello.uala.challenge.follower.application.query;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import java.util.List;

public class GetFollowersHandler {

    private final FollowQueryRepository repository;

    public GetFollowersHandler(FollowQueryRepository repository) {
        this.repository = repository;
    }

    public List<Follow> handle(GetFollowersQuery query) {
        return repository.findFollowers(query.userId());
    }
}