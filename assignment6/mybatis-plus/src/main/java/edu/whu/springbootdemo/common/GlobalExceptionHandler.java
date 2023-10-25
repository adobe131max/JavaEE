package edu.whu.springbootdemo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(CustomException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(exception.getCode());
        response.setMessage(exception.getMessage());
        ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }
}
