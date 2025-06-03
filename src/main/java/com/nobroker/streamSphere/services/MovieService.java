package com.nobroker.streamSphere.services;



import java.time.LocalDateTime;
import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.mappers.MovieMapper;
import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.repositories.MoviesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MovieService {


    @Autowired
    private MoviesRepo moviesRepo;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieGenreService movieGenreService;


    public List<Movie> getAllMovies() {
        return moviesRepo.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return moviesRepo.findById(id);
    }

    public List<Movie> getMoviesSortedByReleaseDateAsc() {
        return moviesRepo.findAllByOrderByReleaseDateAsc();
    }

    public List<Movie> getMoviesSortedByReleaseDateDesc() {
        return moviesRepo.findAllByOrderByReleaseDateDesc();
    }

    public List<Movie> getMoviesSortedByRatingAsc() {
        return moviesRepo.findAllByOrderByRatingAsc();
    }

    public List<Movie> getMoviesSortedByRatingDesc() {
        return moviesRepo.findAllByOrderByRatingDesc();
    }

    public Movie addMovie(MovieRequestDTO movieRequest) {

            // 1. Convert DTO to Movies entity
            Movie movie = movieMapper.toMovie(movieRequest);

            Movie savedMovie = moviesRepo.save(movie);

            if (movieRequest.getGenre() != null && !movieRequest.getGenre().isEmpty()) {
                movieGenreService.saveGenresForMovie(savedMovie.getMovieId(), movieRequest.getGenre());
            }

            return savedMovie;
    }

    public Movie updateMovie(Long id, MovieRequestDTO updatedMovieData) {

        Movie existingMovie = moviesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        if (updatedMovieData.getMovieName()    != null) existingMovie.setMovieName(updatedMovieData.getMovieName());
        if (updatedMovieData.getDescription()  != null) existingMovie.setDescription(updatedMovieData.getDescription());
        if (updatedMovieData.getActorList()    != null) existingMovie.setActorList(String.join(", ",updatedMovieData.getActorList()));
        if (updatedMovieData.getReleaseDate()  != null) existingMovie.setReleaseDate(updatedMovieData.getReleaseDate());
        if (updatedMovieData.getRunTime()      != null) existingMovie.setRunTime(updatedMovieData.getRunTime());
        if (updatedMovieData.getMoviePoster()  != null) existingMovie.setMoviePoster(updatedMovieData.getMoviePoster());
        if (updatedMovieData.getRating() != 0)           existingMovie.setRating(updatedMovieData.getRating());
        if (updatedMovieData.getUpdatedBy()  != null)     existingMovie.setUpdatedBy(updatedMovieData.getUpdatedBy());

        existingMovie.setUpdatedAt(LocalDateTime.now());

        return moviesRepo.save(existingMovie);
    }


    @Transactional
    public void deleteMovieById(Long movieId) {
        // Delete genres first (to avoid FK constraint issues)
        movieGenreService.deleteGenresByMovieId(movieId);

        // Then delete the movie
        moviesRepo.deleteById(movieId);
    }


}
