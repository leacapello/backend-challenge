package ar.com.lcapello.uala.challenge.user.domain.exception;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(String message) {
        super(message);
    }
}