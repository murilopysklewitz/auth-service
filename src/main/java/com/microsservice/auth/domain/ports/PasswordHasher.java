package com.microsservice.auth.domain.ports;

public interface PasswordHasher {
    public String hash(String rawPassword);
    public boolean verify(String rawPassword, String hashedPassword);
}
