package ar.com.lcapello.uala.challenge.timeline.infrastructure.rest.config;

import ar.com.lcapello.uala.challenge.timeline.application.query.GetTimelineByFollowerHandler;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.TimelineQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class TimelineHandlerConfig {

    @Produces
    @ApplicationScoped
    public GetTimelineByFollowerHandler createTweetCommandHandler(TimelineQueryRepository repository,
                                                                  TimelineQueryConfigImpl config) {
        return new GetTimelineByFollowerHandler(repository, config);
    }

}