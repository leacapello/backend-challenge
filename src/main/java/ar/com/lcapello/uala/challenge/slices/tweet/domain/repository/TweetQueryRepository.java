package ar.com.lcapello.uala.challenge.slices.tweet.domain.repository;

import ar.com.lcapello.uala.challenge.slices.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.slices.tweet.domain.vo.TweetID;

import java.util.Optional;

public interface TweetQueryRepository {
    Optional<Tweet> findById(TweetID tweetID);
}