package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.messaging;

import ar.com.lcapello.uala.challenge.slices.timeline.application.command.ProcessTweetCommand;
import ar.com.lcapello.uala.challenge.slices.timeline.application.command.ProcessTweetCommandHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.messaging.dto.CreateTweetEventMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class TweetConsumer {

    @Inject
    private ObjectMapper mapper;

    @Inject
    private ProcessTweetCommandHandler processCommandHandler;

    @Incoming("tweets-consumer")
    @Transactional
    public void onMessage(final String rawTweetEvent) {
        final CreateTweetEventMsg createTweetEvent = toObject(rawTweetEvent);

        processCommandHandler.handle(new ProcessTweetCommand(
                createTweetEvent.tweetId(),
                createTweetEvent.authorId(),
                createTweetEvent.message(),
                createTweetEvent.createdAt()
        ));
    }

    private CreateTweetEventMsg toObject(final String json) {
        try {
            return mapper.readValue(json, CreateTweetEventMsg.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing CreateTweetEventMsg", e);
        }
    }

}