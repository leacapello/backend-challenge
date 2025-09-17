package ar.com.lcapello.uala.challenge.slices.tweet.domain.exception;

public class InvalidTweetException extends RuntimeException {
    public InvalidTweetException(String message) {
        super(message);
    }
}