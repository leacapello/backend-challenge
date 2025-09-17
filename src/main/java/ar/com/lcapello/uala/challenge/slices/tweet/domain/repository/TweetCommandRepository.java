package ar.com.lcapello.uala.challenge.slices.tweet.domain.repository;

import ar.com.lcapello.uala.challenge.slices.tweet.domain.model.Tweet;

public interface TweetCommandRepository {
    void save(Tweet tweet);
}