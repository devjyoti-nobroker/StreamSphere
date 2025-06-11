package com.nobroker.streamSphere.services;

import com.github.cage.Cage;
import com.github.cage.GCage;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaService {
    private final Cage cage = new GCage();
    private final Map<String, String> captchaStorage = new ConcurrentHashMap<>();

    public String generateCaptcha(String key) {
        String text = cage.getTokenGenerator().next();
        captchaStorage.put(key, text);
        return Base64.getEncoder().encodeToString(cage.draw(text));
    }

    public boolean validateCaptcha(String key, String userInput) {
        String realAnswer = captchaStorage.get(key);
        captchaStorage.remove(key);
        return realAnswer != null && realAnswer.equalsIgnoreCase(userInput);
    }

    public String createKey() {
        return UUID.randomUUID().toString();
    }
}

