package ar.com.lcapello.uala.challenge.tweet.domain.exception;

public class InvalidTweetMessageException extends RuntimeException {
    public InvalidTweetMessageException(String message) {
        super(message);
    }
}