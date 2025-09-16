package ar.com.lcapello.uala.challenge.follower.application.command;

import ar.com.lcapello.uala.challenge.follower.domain.model.Follow;
import ar.com.lcapello.uala.challenge.follower.domain.repository.FollowCommandRepository;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;

import java.time.Instant;

public class AddFollowCommandHandler {

    private final FollowCommandRepository repository;

    public AddFollowCommandHandler(FollowCommandRepository repository) {
        this.repository = repository;
    }

    public Follow handle(AddFollowCommand command) {
        final Follow follow = new Follow(
                new UserID(command.followerId()),
                new UserID(command.followedId()),
                Instant.now()
        );
        repository.save(follow);
        return follow;
    }
}


