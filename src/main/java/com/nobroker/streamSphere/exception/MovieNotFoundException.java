package com.nobroker.streamSphere.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long movieId) {
        super("Movie with ID " + movieId + " not found.");
    }
}
