package com.cloud.vijay.health_check.exception;

public class UnAuthorizedException extends Exception {

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}
