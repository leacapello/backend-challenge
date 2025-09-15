package ar.com.lcapello.uala.challenge.user.domain.vo;

import ar.com.lcapello.uala.challenge.user.domain.exception.InvalidUserIdException;

public record UserID(String value) {
    public UserID {
        if (value == null || value.isBlank()) {
            throw new InvalidUserIdException("UserId cannot be null or blank");
        }
    }
}