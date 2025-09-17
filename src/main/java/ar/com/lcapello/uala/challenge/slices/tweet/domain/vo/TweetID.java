package ar.com.lcapello.uala.challenge.slices.tweet.domain.vo;

import ar.com.lcapello.uala.challenge.slices.tweet.domain.exception.InvalidTweetIdException;

public record TweetID(String value) {
    public TweetID {
        if (value == null || value.isBlank()) {
            throw new InvalidTweetIdException("TweetId cannot be null or blank");
        }
    }
}