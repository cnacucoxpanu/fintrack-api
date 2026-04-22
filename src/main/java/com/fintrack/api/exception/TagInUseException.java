package com.fintrack.api.exception;

public class TagInUseException extends RuntimeException {
    public TagInUseException(String message) {
        super(message);
    }
}
