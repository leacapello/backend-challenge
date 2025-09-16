package ar.com.lcapello.uala.challenge.follower.application.query;

import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowQueryRepository;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;

import java.util.List;

public class GetFollowingHandler {

    private final FollowQueryRepository repository;

    public GetFollowingHandler(FollowQueryRepository repository) {
        this.repository = repository;
    }

    public List<UserID> handle(GetFollowingQuery query) {
        return repository.findFollowing(new UserID(query.userID()));
    }
}
