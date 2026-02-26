package com.biblioteca.biblioteca_digital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SuscripcionNoActivaException.class)
    public ResponseEntity<?> handleSuscripcionNoActiva(SuscripcionNoActivaException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }
}
