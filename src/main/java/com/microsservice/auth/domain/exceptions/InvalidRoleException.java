package com.microsservice.auth.domain.exceptions;

public class
InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {
        super(message);
    }
}
