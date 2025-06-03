package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.services.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MovieSearchController {

    @Autowired
    private MovieSearchService movieSearchService;

    @GetMapping("/search")
    public ResponseEntity<?> findByTestSearch(@RequestParam(value = "q") String text) throws IOException {
        return ResponseEntity.ok().body(movieSearchService.searchByText(text));
    }
}
