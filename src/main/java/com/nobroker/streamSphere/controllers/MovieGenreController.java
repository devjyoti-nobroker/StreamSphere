package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.models.MovieGenre;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import com.nobroker.streamSphere.services.MovieGenreService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getMovieCardsByGenre(@PathVariable String genre) {

        try {


            //Cards are mainly used to return those fields that are useful to show

            List<MovieCardProjection> cards = genreService.getMovieCardsByGenre(genre);
            System.out.println(cards.size());
            return cards.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(cards);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch movies by genre: " + e.getMessage());
        }
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
