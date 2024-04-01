package com.cloud.vijay.health_check.exception;

public class ForbiddenException extends Exception {

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
