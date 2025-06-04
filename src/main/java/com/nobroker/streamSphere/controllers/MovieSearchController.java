package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.services.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class MovieSearchController {

    @Autowired
    private MovieSearchService movieSearchService;

    @GetMapping("/search")
    public ResponseEntity<?> findByTestSearch(
            @RequestParam(value = "q", required = false) String text,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "ratingMin", required = false) Float min,
            @RequestParam(value = "ratingMax", required = false) Float max
            ) throws IOException, ParseException {
        return ResponseEntity.ok().body(movieSearchService.searchByTextGenreAndRating(text,genre,min,max));
    }

    @GetMapping("/suggestion")
    public ResponseEntity<?> findSugestions(@RequestParam(value = "q") String text) throws IOException {
        return ResponseEntity.ok().body(movieSearchService.autocomplete(text));
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() throws IOException, ParseException {
        return ResponseEntity.ok().body(movieSearchService.getAllMovies());
    }

}
