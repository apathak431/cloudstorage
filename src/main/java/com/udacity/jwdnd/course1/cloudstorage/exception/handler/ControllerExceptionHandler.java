package com.udacity.jwdnd.course1.cloudstorage.exception.handler;

import com.udacity.jwdnd.course1.cloudstorage.exception.entity.ErrorMessage;
import com.udacity.jwdnd.course1.cloudstorage.exception.type.MethodNotAllowedException;
import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException ex, WebRequest request) {
        return createResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodNotAllowedException.class)
    public ResponseEntity handleMethodNotAllowedException(MethodNotAllowedException ex, WebRequest request) {
        return createResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity createResponseEntity(Exception ex, HttpStatus httpStatus) {
        return new ResponseEntity(
                new ErrorMessage(httpStatus.value(), ex.getMessage(), new Date()),
                new HttpHeaders(),
                httpStatus
        );
    }
}