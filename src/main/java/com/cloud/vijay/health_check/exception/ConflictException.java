package com.cloud.vijay.health_check.exception;

public class ConflictException extends Exception {
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException() {
        super();
    }
}
