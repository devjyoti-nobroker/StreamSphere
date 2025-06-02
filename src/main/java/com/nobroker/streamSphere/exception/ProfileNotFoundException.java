package com.nobroker.streamSphere.exception;

public class ProfileNotFoundException extends RuntimeException{
    public ProfileNotFoundException(Long profileId) {
        super("Profile by the id " + profileId + " not found!");
    }
}
