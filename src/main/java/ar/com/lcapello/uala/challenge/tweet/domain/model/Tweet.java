package ar.com.lcapello.uala.challenge.tweet.domain.model;

import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetException;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;

import java.time.Instant;

public class Tweet {

    // IDEA: si en el futuro el límite de caracteres deja de ser fijo, podría moverse a una factory y recibirlo como parámetro externo.
    private static final int MAX_LENGTH = 280;

    private TweetID tweetID;
    private String authorID;
    private String message;
    private Instant createdAt;

    public Tweet(TweetID tweetID, String authorID, String message, Instant createdAt) {
        if (message == null || message.isBlank()) {
            throw new InvalidTweetException("Message cannot be null or blank");
        }
        if (message.length() > MAX_LENGTH) {
            throw new InvalidTweetException("Message cannot exceed " + MAX_LENGTH + " characters");
        }
        if (createdAt == null) {
            throw new InvalidTweetException("createdAt cannot be null");
        }

        this.tweetID = tweetID;
        this.authorID = authorID;
        this.message = message;
        this.createdAt = createdAt;
    }

    public TweetID getTweetID() {
        return tweetID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}