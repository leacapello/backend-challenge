package ar.com.lcapello.uala.challenge.timeline.domain.exception;

public class InvalidTimelineException extends RuntimeException {
    public InvalidTimelineException(String message) {
        super(message);
    }
}