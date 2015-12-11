package com.splunk.hollywood.controller;

import com.splunk.hollywood.dto.Errors;
import com.splunk.hollywood.exception.AuthFailException;
import com.splunk.hollywood.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value={
            NotFoundException.class,
            AuthFailException.class
    })
    public ResponseEntity<Object> generalHandleException(Exception ex) {
        Errors error = new Errors();
        HttpStatus status = ex.getClass().getAnnotation(ResponseStatus.class).value();
        error.setCode(status.toString());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<Object>(error, status);
    }

}
