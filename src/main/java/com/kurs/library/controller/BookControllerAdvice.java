package com.kurs.library.controller;

import com.kurs.library.exception.BookException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BookControllerAdvice {

    @ResponseBody
    @ExceptionHandler({BookException.class})
    public ResponseEntity<ApiError> internalExceptionHandler(BookException e) {
        final Map<String, String> errors = new HashMap<>();
        final String errorMessage = e.getMessage();
        errors.put(e.getName(), errorMessage);
        final ApiError apiError = new ApiError(e.getStatus(), e.getStatus(), e.getMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
