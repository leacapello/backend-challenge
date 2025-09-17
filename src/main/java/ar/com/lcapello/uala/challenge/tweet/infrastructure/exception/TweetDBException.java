package ar.com.lcapello.uala.challenge.tweet.infrastructure.exception;

public class TweetDBException extends RuntimeException {
    public TweetDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
