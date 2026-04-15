package com.microsservice.auth.domain.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String email) {
        super("Email '" + email + "' is already registered.");
    }
}
