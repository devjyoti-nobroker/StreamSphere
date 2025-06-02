package com.nobroker.streamSphere.exception;

public class UnauthorizedProfileAccessException extends RuntimeException {
    public UnauthorizedProfileAccessException(Long profileId){
        super("The profile " + profileId + " does not belong to the user!");
    }
}
