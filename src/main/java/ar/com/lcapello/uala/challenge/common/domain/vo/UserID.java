package ar.com.lcapello.uala.challenge.common.domain.vo;

import ar.com.lcapello.uala.challenge.common.domain.exception.InvalidUserIdException;

public record UserID(String value) {
    public UserID {
        if (value == null || value.isBlank()) {
            throw new InvalidUserIdException("UserId cannot be null or blank");
        }
    }
}