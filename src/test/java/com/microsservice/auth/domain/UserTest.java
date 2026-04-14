package com.microsservice.auth.domain;

import com.microsservice.auth.domain.exceptions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void shouldCreateUserWithValidData() {
        String email = "test@example.com";
        String passwordHash = "hashedpassword";
        UserRole role = UserRole.USER;

        User user = User.create(email, passwordHash, role);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(role, user.getRole());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertNull(user.getId());
        assertTrue(user.isActivate());
        assertFalse(user.isBlocked());
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsNull() {
        assertThrows(InvalidEmailException.class, () -> {
            User.create(null, "hash", UserRole.USER);
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsBlank() {
        assertThrows(InvalidEmailException.class, () -> {
            User.create("", "hash", UserRole.USER);
        });
        assertThrows(InvalidEmailException.class, () -> {
            User.create("   ", "hash", UserRole.USER);
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsTooShort() {
        assertThrows(InvalidEmailException.class, () -> {
            User.create("a@b", "hash", UserRole.USER);
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsTooLong() {
        String longEmail = "a".repeat(101) + "@example.com";
        assertThrows(InvalidEmailException.class, () -> {
            User.create(longEmail, "hash", UserRole.USER);
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailFormatIsInvalid() {
        assertThrows(InvalidEmailException.class, () -> {
            User.create("invalidemail", "hash", UserRole.USER);
        });
        assertThrows(InvalidEmailException.class, () -> {
            User.create("test@", "hash", UserRole.USER);
        });
        assertThrows(InvalidEmailException.class, () -> {
            User.create("@example.com", "hash", UserRole.USER);
        });
    }

    @Test
    void shouldThrowInvalidPasswordExceptionWhenPasswordHashIsNull() {
        assertThrows(InvalidPasswordException.class, () -> {
            User.create("test@example.com", null, UserRole.USER);
        });
    }

    @Test
    void shouldThrowInvalidPasswordExceptionWhenPasswordHashIsBlank() {
        assertThrows(InvalidPasswordException.class, () -> {
            User.create("test@example.com", "", UserRole.USER);
        });
        assertThrows(InvalidPasswordException.class, () -> {
            User.create("test@example.com", "   ", UserRole.USER);
        });
    }

    @Test
    void shouldThrowInvalidRoleExceptionWhenRoleIsNull() {
        assertThrows(InvalidRoleException.class, () -> {
            User.create("test@example.com", "hash", null);
        });
    }

    @Test
    void shouldRestoreUser() {
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String passwordHash = "hash";
        UserRole role = UserRole.ADMIN;
        UserStatus status = UserStatus.INACTIVE;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        User user = User.restore(id, email, passwordHash, role, status, createdAt, updatedAt);

        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(role, user.getRole());
        assertEquals(status, user.getStatus());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());
    }

    @Test
    void shouldReturnTrueForIsActivateWhenStatusIsActive() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        assertTrue(user.isActivate());
    }

    @Test
    void shouldReturnFalseForIsActivateWhenStatusIsNotActive() {
        User user = User.restore(UUID.randomUUID(), "test@example.com", "hash", UserRole.USER, UserStatus.INACTIVE, LocalDateTime.now(), LocalDateTime.now());
        assertFalse(user.isActivate());
    }

    @Test
    void shouldReturnTrueForIsBlockedWhenStatusIsBlocked() {
        User user = User.restore(UUID.randomUUID(), "test@example.com", "hash", UserRole.USER, UserStatus.BLOCKED, LocalDateTime.now(), LocalDateTime.now());
        assertTrue(user.isBlocked());
    }

    @Test
    void shouldReturnFalseForIsBlockedWhenStatusIsNotBlocked() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        assertFalse(user.isBlocked());
    }

    @Test
    void shouldBlockUser() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        assertFalse(user.isBlocked());

        user.block();
        assertTrue(user.isBlocked());
        assertEquals(UserStatus.BLOCKED, user.getStatus());
    }

    @Test
    void shouldNotChangeStatusIfAlreadyBlocked() {
        User user = User.restore(UUID.randomUUID(), "test@example.com", "hash", UserRole.USER, UserStatus.BLOCKED, LocalDateTime.now(), LocalDateTime.now());
        user.block();
        assertTrue(user.isBlocked());
    }

    @Test
    void shouldChangeEmailWithValidEmail() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        String newEmail = "new@example.com";

        user.changeEmail(newEmail);
        assertEquals(newEmail, user.getEmail());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenChangingToNullEmail() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        assertThrows(InvalidEmailException.class, () -> {
            user.changeEmail(null);
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenChangingToBlankEmail() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        assertThrows(InvalidEmailException.class, () -> {
            user.changeEmail("");
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenChangingToTooShortEmail() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        assertThrows(InvalidEmailException.class, () -> {
            user.changeEmail("a@b");
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenChangingToTooLongEmail() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        String longEmail = "a".repeat(101) + "@example.com";
        assertThrows(InvalidEmailException.class, () -> {
            user.changeEmail(longEmail);
        });
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenChangingToInvalidFormatEmail() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        assertThrows(InvalidEmailException.class, () -> {
            user.changeEmail("invalid");
        });
    }

    @Test
    void shouldThrowUserBlockedExceptionWhenChangingEmailOnBlockedUser() {
        User user = User.restore(UUID.randomUUID(), "test@example.com", "hash", UserRole.USER, UserStatus.BLOCKED, LocalDateTime.now(), LocalDateTime.now());
        assertThrows(UserBlockedException.class, () -> {
            user.changeEmail("new@example.com");
        });
    }

    @Test
    void shouldChangeStatus() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        user.changeStatus(UserStatus.INACTIVE);
        assertEquals(UserStatus.INACTIVE, user.getStatus());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void shouldThrowUserBlockedExceptionWhenChangingStatusOnBlockedUser() {
        User user = User.restore(UUID.randomUUID(), "test@example.com", "hash", UserRole.USER, UserStatus.BLOCKED, LocalDateTime.now(), LocalDateTime.now());
        assertThrows(UserBlockedException.class, () -> {
            user.changeStatus(UserStatus.ACTIVE);
        });
    }

    @Test
    void shouldReturnCorrectToString() {
        User user = User.create("test@example.com", "hash", UserRole.USER);
        String toString = user.toString();
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("USER"));
        assertTrue(toString.contains("ACTIVE"));
    }
}
