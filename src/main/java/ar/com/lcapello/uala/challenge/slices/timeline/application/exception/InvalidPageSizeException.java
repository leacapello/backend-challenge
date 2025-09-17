package ar.com.lcapello.uala.challenge.slices.timeline.application.exception;

public class InvalidPageSizeException extends RuntimeException {
    public InvalidPageSizeException(String message) {
        super(message);
    }
}
