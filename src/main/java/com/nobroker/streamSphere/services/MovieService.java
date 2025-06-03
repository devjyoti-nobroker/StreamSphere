package com.nobroker.streamSphere.services;



import java.time.LocalDateTime;
import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.models.Movies;
import com.nobroker.streamSphere.repositories.MoviesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;


@Service
public class MovieService {


    @Autowired
    private MoviesRepo moviesRepo;

    @Autowired
    private MovieGenreService movieGenreService;


    public List<Movies> getAllMovies() {
        return moviesRepo.findAll();
    }

    public Optional<Movies> getMovieById(Long id) {
        return moviesRepo.findById(id);
    }

    public List<Movies> getMoviesSortedByReleaseDateAsc() {
        return moviesRepo.findAllByOrderByReleaseDateAsc();
    }

    public List<Movies> getMoviesSortedByReleaseDateDesc() {
        return moviesRepo.findAllByOrderByReleaseDateDesc();
    }

    public List<Movies> getMoviesSortedByRatingAsc() {
        return moviesRepo.findAllByOrderByRatingAsc();
    }

    public List<Movies> getMoviesSortedByRatingDesc() {
        return moviesRepo.findAllByOrderByRatingDesc();
    }

    public Movies addMovie(Movies movie) {
        LocalDateTime now = LocalDateTime.now();
        movie.setCreatedAt(now);
        movie.setUpdatedAt(now);
        return moviesRepo.save(movie);
    }




    public Movies updateMovie(Long id, MovieRequestDTO updatedMovieData) {

        Movies existingMovie = moviesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        if (updatedMovieData.getMovieName()    != null) existingMovie.setMovieName(updatedMovieData.getMovieName());
        if (updatedMovieData.getDescription()  != null) existingMovie.setDescription(updatedMovieData.getDescription());
        if (updatedMovieData.getActorList()    != null) existingMovie.setActorList(updatedMovieData.getActorList());
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
