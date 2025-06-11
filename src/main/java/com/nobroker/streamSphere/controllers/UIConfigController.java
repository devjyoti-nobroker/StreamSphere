package com.nobroker.streamSphere.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/ui-config")
public class UIConfigController {

    @GetMapping
    public ResponseEntity<String> getUIConfig() throws IOException {
        ClassPathResource resource = new ClassPathResource("config/ui-config.json");
        String json = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        return ResponseEntity.ok(json);
    }
}
