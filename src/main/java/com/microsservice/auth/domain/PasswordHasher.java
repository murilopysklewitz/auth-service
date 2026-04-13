package com.microsservice.auth.domain;

public interface PasswordHasher {
    public String hash(String rawPassword);
    public boolean verify(String rawPassword, String hashedPassword);
}
