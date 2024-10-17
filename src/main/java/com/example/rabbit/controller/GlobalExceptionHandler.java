package com.example.rabbit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by vladimirsabo on 17.10.2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String VALIDATION_FAILED_PREFIX = "Validation failed: ";
    public static final String UNEXPECTED_ERROR_PREFIX = "An error occurred: ";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError == null ? "unexpected validation error" : fieldError.getDefaultMessage();
        return new ResponseEntity<>(VALIDATION_FAILED_PREFIX + message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralExceptions(Exception e) {
        return new ResponseEntity<>(UNEXPECTED_ERROR_PREFIX + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}