package com.nobroker.streamSphere.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingSearchParameterException extends RuntimeException {
    public MissingSearchParameterException(String message) {
        super(message);
    }
}