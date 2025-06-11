package com.nobroker.streamSphere.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.repositories.MovieGenreRepo;
import com.nobroker.streamSphere.services.MovieGenreService;
import com.nobroker.streamSphere.services.MovieService;
import com.nobroker.streamSphere.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
//@CrossOrigin
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieGenreRepo movieGenreRepo;

    @Autowired
    private MovieGenreService movieGenreService;

    @Autowired
    private RedisService redisService;


    private static final String MOVIE_CACHE_KEY = "movies";
    private static final String SORT_DATE_MOVIE_CACHE_KEY = "sortMovies";
    private static final String SORT_IMDB_MOVIE_CACHE_KEY = "sortImdbMovies";





//    public MoviesController(MovieService movieService, RedisService redisService) {
//        this.movieService = movieService;
//        this.redisService = redisService;
//    }


    // To display all the movies (unsorted) (might be redundant)

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieService.getAllMovies(pageable);

        return ResponseEntity.ok().body(moviePage);
    }



    //Get a particular movie by the id

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
            //Optional ds is mainly used in case we don't have a movie with the id,it returns NULL
            return ResponseEntity.ok().body(movieService.getMovieResponseById(id));
    }


    // Sorted the movies according to release date ascending (redundant)

    @GetMapping("/movies/sort/releaseDate/asc")
    public ResponseEntity<?> getMoviesSortedByReleaseDateAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Movie> movies = movieService.getMoviesSortedByReleaseDateAsc(pageable);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }


    // Sorted the movies according to release date descending

    @GetMapping("/movies/recent")
    public Page<Movie> getMoviesSortedByReleaseDateDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieService.getMoviesSortedByReleaseDateDesc(pageable);
    }


    // Sorted the movies according to rating ascending (redundant)

    @GetMapping("/movies/sort/rating/asc")
    public ResponseEntity<?> getMoviesSortedByRatingAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try{
            Page<Movie> movies = movieService.getMoviesSortedByRatingAsc(pageable);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching movies: " + e.getMessage());
        }
    }

    // Sorted the movies according to rating descending

    @GetMapping("/movies/popular")
    public Page<Movie>  getMoviesSortedByRatingDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieService.getMoviesSortedByRatingDesc(pageable);
//        try {
//            List <Movie> movies = movieService.getMoviesSortedByRatingDesc();
//            return ResponseEntity.ok(movies);
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error fetching movies: " + e.getMessage());
//        }
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
