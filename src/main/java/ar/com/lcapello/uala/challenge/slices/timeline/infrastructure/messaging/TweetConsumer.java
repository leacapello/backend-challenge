package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.messaging;

import ar.com.lcapello.uala.challenge.slices.timeline.application.command.CreateTweetEvent;
import ar.com.lcapello.uala.challenge.slices.timeline.application.command.ProcessTweetCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class TweetConsumer {

    @Inject
    private ProcessTweetCommandHandler processCommandHandler;

    @Incoming("tweets-consumer")
    @Transactional
    public void onMessage(final CreateTweetEvent tweetEvent) {
        processCommandHandler.handle(tweetEvent);
    }

}
