package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.services.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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
        Map<String,Object> json = new HashMap<>();
        json.put("suggestions",movieSearchService.autocomplete(text));
        json.put("movies",movieSearchService.searchByTextGenreAndRating(text,genre,min,max));
        return ResponseEntity.ok().body(json);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() throws IOException, ParseException {
        return ResponseEntity.ok().body(movieSearchService.getAllMovies());
    }

}
