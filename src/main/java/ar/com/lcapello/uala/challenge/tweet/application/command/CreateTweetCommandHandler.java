package ar.com.lcapello.uala.challenge.tweet.application.command;

import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetEventPublisher;
import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetCommandRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;

import java.time.Instant;

public class CreateTweetCommandHandler {

    private final TweetCommandRepository repository;
    private final TweetEventPublisher publisher;

    public CreateTweetCommandHandler(TweetCommandRepository repository, TweetEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public Tweet handle(CreateTweetCommand command) {
        final Tweet tweet = new Tweet(
                new TweetID(command.tweetID()),
                command.authorID(),
                command.message(),
                Instant.now()
        );
        repository.save(tweet);         // se puede aplicar patron outbox para evitar inconsistencias
        publisher.publish(tweet);       //
        return tweet;
    }

}