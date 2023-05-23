package com.kurs.library.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class ApiError {

    private HttpStatus status;
    private Integer statusCode;
    private String message;
    private Map<String, String> errors;

    public ApiError(final HttpStatus status, final HttpStatus statusCode, final String message, final Map<String, String> errors) {
        super();
        this.status = status;
        this.statusCode = statusCode.value();
        this.message = message;
        this.errors = errors;
    }
}
