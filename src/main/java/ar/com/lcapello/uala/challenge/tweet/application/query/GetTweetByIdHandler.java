package ar.com.lcapello.uala.challenge.tweet.application.query;

import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetQueryRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;

import java.util.Optional;

public class GetTweetByIdHandler {

    private final TweetQueryRepository repository;

    public GetTweetByIdHandler(TweetQueryRepository repository) {
        this.repository = repository;
    }

    public Optional<Tweet> handle(GetTweetByIdQuery query) {
        final TweetID tweetID = new TweetID(query.tweetID());
        return repository.findById(tweetID);
    }

}