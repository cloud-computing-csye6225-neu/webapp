package com.cloud.vijay.health_check.exception;

import com.cloud.vijay.health_check.service.UserService;

public class BadRequestException extends Exception{
	
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
