package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.config;

import ar.com.lcapello.uala.challenge.slices.timeline.application.command.ProcessTweetCommandHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.FollowersReader;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineCommandRepository;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class TimelineHandlerConfig {

    @Produces
    @ApplicationScoped
    public GetTimelineByFollowerHandler createTweetCommandHandler(TimelineQueryRepository repository) {
        return new GetTimelineByFollowerHandler(repository);
    }

    @Produces
    @ApplicationScoped
    public ProcessTweetCommandHandler createProcessTweetCommandHandler(FollowersReader followersReader, TimelineCommandRepository repository) {
        return new ProcessTweetCommandHandler(followersReader, repository);
    }

}