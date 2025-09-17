package ar.com.lcapello.uala.challenge.slices.tweet.domain.repository;

import ar.com.lcapello.uala.challenge.slices.tweet.domain.model.Tweet;

public interface TweetEventPublisher {

    void publish(Tweet tweet);

}