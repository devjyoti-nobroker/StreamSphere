package com.nobroker.streamSphere.util;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private static final ThreadLocal<String> email = new ThreadLocal<>();
    private static final ThreadLocal<Long> profileId = new ThreadLocal<>();

    public void setEmail(String emailValue) {
        email.set(emailValue);
    }

    public String getEmail() {
        return email.get();
    }

    public void setProfileId(Long id) {
        profileId.set(id);
    }

    public Long getProfileId() {
        return profileId.get();
    }

    public void clear() {
        email.remove();
        profileId.remove();
    }
}
