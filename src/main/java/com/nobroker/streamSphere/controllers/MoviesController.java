package com.nobroker.streamSphere.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobroker.streamSphere.models.Movies;
import com.nobroker.streamSphere.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class MoviesController {

    @Autowired
    private MovieService movieService;



    @GetMapping("/movies")
    public ResponseEntity<?> getMovies() {
        try {
            // Ok
            List<Movies> movies = movieService.getAllMovies();
            return ResponseEntity.ok(movies);

        } catch (Exception e) {
            // Log the error if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch movies: " + e.getMessage());
        }
    }



    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        try {
            Optional<Movies> movie = movieService.getMovieById(id);
            if (movie.isPresent()) {
                return ResponseEntity.ok(movie.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Movie with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movie: " + e.getMessage());
        }
    }



    @GetMapping("/movies/sort/releaseDate/asc")
    public ResponseEntity<?> getMoviesSortedByReleaseDateAsc() {
        try {
            List<Movies> movies = movieService.getMoviesSortedByReleaseDateAsc();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }



    @GetMapping("/movies/sort/releaseDate/desc")
    public ResponseEntity<?> getMoviesSortedByReleaseDateDesc() {
        try {
            List<Movies> movies = movieService.getMoviesSortedByReleaseDateDesc();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }

    @GetMapping("/movies/sort/rating/asc")
    public ResponseEntity<?> getMoviesSortedByRatingAsc() {
        try{
            List<Movies> movies = movieService.getMoviesSortedByRatingAsc();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }


    @GetMapping("/movies/sort/rating/desc")
    public ResponseEntity<?>getMoviesSortedByRatingDesc() {
        try {
            List <Movies> movies = movieService.getMoviesSortedByRatingDesc();
            return ResponseEntity.ok(movies);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }




    @PostMapping(value = "/movies", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addMovie(@RequestPart("movie") String movieJson,
                                      @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            // Manually convert JSON string to Movies object
            ObjectMapper objectMapper = new ObjectMapper();
            Movies movie = objectMapper.readValue(movieJson, Movies.class);

            Movies savedMovie = movieService.addMovie(movie, imageFile);
            return ResponseEntity.ok(savedMovie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving movie: " + e.getMessage());
        }
    }



    @PutMapping(value = "/movies/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateMovie(@PathVariable Long id,
                                         @RequestPart("movie") String movieJson,
                                         @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Movies movie = objectMapper.readValue(movieJson, Movies.class);

            Movies updatedMovie = movieService.updateMovie(id, movie, imageFile);

            return ResponseEntity.ok(updatedMovie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating movie: " + e.getMessage());
        }
    }













}
