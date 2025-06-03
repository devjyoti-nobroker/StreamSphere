package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.repositories.MovieGenreRepo;
import com.nobroker.streamSphere.services.MovieGenreService;
import com.nobroker.streamSphere.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieGenreRepo movieGenreRepo;

    @Autowired
    private MovieGenreService movieGenreService;



    // To display all the movies (unsorted) (might be redundant)

    @GetMapping("/movies")
    public ResponseEntity<?> getMovies() {
        try {
            // Ok
            List<Movie> movies = movieService.getAllMovies();
            return ResponseEntity.ok(movies);

        } catch (Exception e) {
            // Log the error if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch movies: " + e.getMessage());
        }
    }


    //Get a particular movie by the id

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        try {

            //Optional ds is mainly used in case we don't have a movie with the id,it returns NULL


            Optional<Movie> movie = movieService.getMovieById(id);
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


    // Sorted the movies according to release date ascending (redundant)

    @GetMapping("/movies/sort/releaseDate/asc")
    public ResponseEntity<?> getMoviesSortedByReleaseDateAsc() {
        try {
            List<Movie> movies = movieService.getMoviesSortedByReleaseDateAsc();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }


    // Sorted the movies according to release date descending

    @GetMapping("/movies/sort/releaseDate/desc")
    public ResponseEntity<?> getMoviesSortedByReleaseDateDesc() {
        try {
            List<Movie> movies = movieService.getMoviesSortedByReleaseDateDesc();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }


    // Sorted the movies according to rating ascending (redundant)

    @GetMapping("/movies/sort/rating/asc")
    public ResponseEntity<?> getMoviesSortedByRatingAsc() {
        try{
            List<Movie> movies = movieService.getMoviesSortedByRatingAsc();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }

    // Sorted the movies according to rating descending

    @GetMapping("/movies/sort/rating/desc")
    public ResponseEntity<?>getMoviesSortedByRatingDesc() {
        try {
            List <Movie> movies = movieService.getMoviesSortedByRatingDesc();
            return ResponseEntity.ok(movies);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }



    //Here we post the movie.
    //We initially gat the entire movie object along with genre
    //we get it inform of a dto
    //we first save the movie data
    //The id we get is then used to save the genres

    @PostMapping("/movies")
    public ResponseEntity<?> addMovie(@RequestBody MovieRequestDTO movieRequest) {
        return ResponseEntity.ok().body(movieService.addMovie(movieRequest));
    }



    //Similar to post
    //If any entity is empty then the earlier value is put here
    //In case the genre array is empty the previous one is kept
    //Else first the initial genres are deleted
    //Then new ones are added

    @PutMapping("/movies/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id,
                                         @RequestBody MovieRequestDTO movieRequest) {
        try {

            Movie updatedMovie = movieService.updateMovie(id, movieRequest);
            List<String> genres = movieRequest.getGenre();
            if (genres != null && !genres.isEmpty()) {
                movieGenreService.deleteGenresByMovieId(id);
                movieGenreService.saveGenresForMovie(id, genres);
            }
            return ResponseEntity.ok(updatedMovie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating movie: " + e.getMessage());
        }
    }

    //Delete the movie object
    //First delete the genres to resolve FK constraint
    //Then delete the movieID

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        try {
            movieService.deleteMovieById(id);
            return ResponseEntity.ok("Movie deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting movie: " + e.getMessage());
        }
    }
















}
