package ar.com.lcapello.uala.challenge.slices.follower.infrastructure.config;

import ar.com.lcapello.uala.challenge.slices.follower.application.command.AddFollowCommandHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.command.RemoveFollowCommandHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.query.GetFollowersHandler;
import ar.com.lcapello.uala.challenge.slices.follower.application.query.GetFollowingHandler;
import ar.com.lcapello.uala.challenge.slices.follower.infrastructure.persistence.FollowPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class FollowerHandlerConfig {

    @Produces
    @ApplicationScoped
    public AddFollowCommandHandler addFollowCommandHandler(FollowPanacheRepository repository) {
        return new AddFollowCommandHandler(repository);
    }

    @Produces
    @ApplicationScoped
    public RemoveFollowCommandHandler removeFollowCommandHandler(FollowPanacheRepository repository) {
        return new RemoveFollowCommandHandler(repository);
    }

    @Produces
    @ApplicationScoped
    public GetFollowersHandler getFollowersHandler(FollowPanacheRepository repository) {
        return new GetFollowersHandler(repository);
    }

    @Produces
    @ApplicationScoped
    public GetFollowingHandler getFollowingHandler(FollowPanacheRepository repository) {
        return new GetFollowingHandler(repository);
    }
}


