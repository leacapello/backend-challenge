package ar.com.lcapello.uala.challenge.tweet.application.command;

import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetCommandRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;

import java.time.Instant;

public class CreateTweetCommandHandler {

    private final TweetCommandRepository repository;

    public CreateTweetCommandHandler(TweetCommandRepository repository) {
        this.repository = repository;
    }

    public Tweet handle(CreateTweetCommand command) {
        final Tweet tweet = new Tweet(
                new TweetID(command.tweetID()),
                new UserID(command.authorID()),
                command.message(),
                Instant.now()
        );
        repository.save(tweet);
        return tweet;
    }

}