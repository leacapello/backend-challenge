package ar.com.lcapello.uala.challenge.tweet.infrastructure.messaging;

import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetEventPublisher;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TweetProducer implements TweetEventPublisher {

    @Inject
    @Channel("tweets-producer")
    private MutinyEmitter<String> emitter;

    @Inject
    private ObjectMapper mapper;

    @Override
    public void publish(final Tweet tweet) {
        final String json = toJson(tweet);
        emitter.sendAndAwait(json);
    }

    private String toJson(final Tweet tweet) {
        try {
            return mapper.writeValueAsString(tweet);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Tweet to JSON", e);
        }
    }
}