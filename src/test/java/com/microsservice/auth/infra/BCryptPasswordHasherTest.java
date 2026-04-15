package com.microsservice.auth.infra;

import com.microsservice.auth.infra.security.BCryptPasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BCryptPasswordHasherTest {

    @Test
    void shouldHashPassword() {
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();
        String hash = hasher.hash("password");
        assertNotNull(hash);
        assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2b$") || hash.startsWith("$2y$"));
    }

    @Test
    void shouldVerifyCorrectPassword() {
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();
        String password = "password";
        String hash = hasher.hash(password);
        assertTrue(hasher.verify(password, hash));
    }

    @Test
    void shouldNotVerifyIncorrectPassword() {
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();
        String hash = hasher.hash("password");
        assertFalse(hasher.verify("wrong", hash));
    }


    @Test
    void shouldProduceDifferentHashesForSamePassword() {
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();
        String hash1 = hasher.hash("password");
        String hash2 = hasher.hash("password");
        assertNotEquals(hash1, hash2);
    }
}
