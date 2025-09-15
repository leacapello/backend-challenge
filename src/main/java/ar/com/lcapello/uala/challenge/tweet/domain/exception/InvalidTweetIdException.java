package ar.com.lcapello.uala.challenge.tweet.domain.exception;

public class InvalidTweetIdException extends RuntimeException {
    public InvalidTweetIdException(String message) {
        super(message);
    }
}