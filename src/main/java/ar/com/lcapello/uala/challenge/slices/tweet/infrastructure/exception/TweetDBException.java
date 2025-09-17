package ar.com.lcapello.uala.challenge.slices.tweet.infrastructure.exception;

public class TweetDBException extends RuntimeException {
    public TweetDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
