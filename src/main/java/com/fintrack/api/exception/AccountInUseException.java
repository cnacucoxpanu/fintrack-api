package com.fintrack.api.exception;

public class AccountInUseException extends RuntimeException {
    public AccountInUseException(String message) {
        super(message);
    }
}
