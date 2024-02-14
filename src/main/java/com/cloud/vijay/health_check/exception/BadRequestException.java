package com.cloud.vijay.health_check.exception;

public class BadRequestException extends Exception {

    public BadRequestException(String errMessage, Throwable cause) {
        super(errMessage, cause);
    }

    public BadRequestException() {
        super();
    }

    public BadRequestException(String errMessage) {
        super(errMessage);
    }
}
