package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.models.MovieGenre;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import com.nobroker.streamSphere.services.MovieGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class MovieGenreController {

    @Autowired
    private MovieGenreService genreService;



    //Gets all the movies for a particular genre

    @GetMapping("/movies/genre/{genre}")
    public ResponseEntity<?> getMovieCardsByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> cards = genreService.getMovieCardsByGenre(genre, pageable);

        return cards.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cards);
    }




    //Return all the genres of a particular movie

    @GetMapping("/movies/{movieId}/genres")
    public ResponseEntity<?> getGenresForMovie(@PathVariable Long movieId) {
        try {
            List<String> genres = genreService.getGenresByMovieId(movieId);
            return genres.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(genres);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch genres: " + e.getMessage());
        }
    }







}
