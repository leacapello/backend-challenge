package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.config;

import ar.com.lcapello.uala.challenge.slices.timeline.application.command.ProcessTweetCommandHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.FollowerReader;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineCommandRepository;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class TimelineHandlerConfig {

    @Produces
    @ApplicationScoped
    public GetTimelineByFollowerHandler createTweetCommandHandler(TimelineQueryRepository repository, TimelineQueryConfigImpl config) {
        return new GetTimelineByFollowerHandler(repository, config);
    }

    @Produces
    @ApplicationScoped
    public ProcessTweetCommandHandler createProcessTweetCommandHandler(FollowerReader followerReader, TimelineCommandRepository repository) {
        return new ProcessTweetCommandHandler(followerReader, repository);
    }

}