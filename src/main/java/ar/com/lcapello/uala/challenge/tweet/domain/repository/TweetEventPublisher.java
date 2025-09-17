package ar.com.lcapello.uala.challenge.tweet.domain.repository;

import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;

public interface TweetEventPublisher {

    void publish(Tweet tweet);

}