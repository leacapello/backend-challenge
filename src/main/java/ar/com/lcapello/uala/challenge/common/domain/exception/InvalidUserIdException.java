package ar.com.lcapello.uala.challenge.common.domain.exception;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(String message) {
        super(message);
    }
}