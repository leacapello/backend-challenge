package ar.com.lcapello.uala.challenge.tweet.domain.vo;

import ar.com.lcapello.uala.challenge.tweet.domain.exception.InvalidTweetMessageException;

public record TweetMessage(String message) {

    // IDEA: si en el futuro el límite de caracteres deja de ser fijo, podría moverse a una factory y recibirlo como parámetro externo.
    private static final int MAX_LENGTH = 280;

    public TweetMessage {
        if (message == null || message.isBlank()) {
            throw new InvalidTweetMessageException("Message cannot be null or blank");
        }
        if (message.length() > MAX_LENGTH) {
            throw new InvalidTweetMessageException("Message cannot exceed " + MAX_LENGTH + " characters");
        }
    }

}