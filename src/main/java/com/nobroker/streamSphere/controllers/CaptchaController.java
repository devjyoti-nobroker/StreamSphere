package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.services.CaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> getCaptcha() {
        String key = captchaService.createKey();
        String imageBase64 = captchaService.generateCaptcha(key);
        Map<String, String> response = new HashMap<>();
        response.put("key", key);
        response.put("image", "data:image/png;base64," + imageBase64);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> body) {
        String key = body.get("key");
        String input = body.get("input");

        boolean isValid = captchaService.validateCaptcha(key, input);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}

