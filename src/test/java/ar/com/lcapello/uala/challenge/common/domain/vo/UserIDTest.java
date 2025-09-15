package ar.com.lcapello.uala.challenge.common.domain.vo;

import ar.com.lcapello.uala.challenge.common.domain.exception.InvalidUserIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserIDTest {

    @Test
    void shouldCreateUserIDWhenValueIsValid() {
        UserID userID = new UserID("abc123");
        assertEquals("abc123", userID.value());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        InvalidUserIdException ex = assertThrows(
                InvalidUserIdException.class,
                () -> new UserID(null)
        );
        assertEquals("UserId cannot be null or blank", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsBlank() {
        InvalidUserIdException ex = assertThrows(
                InvalidUserIdException.class,
                () -> new UserID("   ")
        );
        assertEquals("UserId cannot be null or blank", ex.getMessage());
    }
}