package com.cloud.vijay.health_check;

import com.cloud.vijay.health_check.exception.BadRequestException;
import com.cloud.vijay.health_check.exception.ConflictException;
import com.cloud.vijay.health_check.exception.ForbiddenException;
import com.cloud.vijay.health_check.exception.UnAuthorizedException;
import org.hibernate.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Void> handleValidationException(Exception ex) {
        System.err.println(ex.getMessage());
//		ex.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({ServiceUnavailableException.class, ConnectException.class, TransactionException.class})
    public ResponseEntity<Void> handleServiceUnAvailableException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Void> handleConflictException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Void> handleUnAuthorizedException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Void> handleMediaTypeNotSupportedException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Void> handleRequestParamException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Void> handleForbiddenException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }
}
