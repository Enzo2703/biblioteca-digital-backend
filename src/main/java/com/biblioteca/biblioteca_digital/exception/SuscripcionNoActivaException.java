package com.biblioteca.biblioteca_digital.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SuscripcionNoActivaException extends RuntimeException{
	
	public SuscripcionNoActivaException(String message) {
        super(message);
    }

}
