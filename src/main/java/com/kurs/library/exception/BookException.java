package com.kurs.library.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BookException extends Exception {
    private String name;
    private HttpStatus status;
    private Integer statusCode;
    private String message;
}
