package ar.com.lcapello.uala.challenge.slices.tweet.domain.exception;

public class InvalidTweetIdException extends RuntimeException {
    public InvalidTweetIdException(String message) {
        super(message);
    }
}