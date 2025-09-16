package ar.com.lcapello.uala.challenge.follower.application.query;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;

import java.util.List;
import java.util.Optional;

public class GetFollowingHandler {

    private final FollowQueryRepository repository;

    public GetFollowingHandler(FollowQueryRepository repository) {
        this.repository = repository;
    }

    public Optional<Follow> handle(GetFollowingQuery query) {
        return repository.find(new UserID(query.followerID()), new UserID(query.followerID()));
    }

}