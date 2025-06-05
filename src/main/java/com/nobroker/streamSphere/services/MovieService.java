package com.nobroker.streamSphere.services;



import java.time.LocalDateTime;
import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.dtos.MovieResponseDTO;
import com.nobroker.streamSphere.mappers.MovieMapper;
import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.models.MovieSearch;
import com.nobroker.streamSphere.repositories.MovieSearchRepository;
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
    private MovieSearchRepository movieSearchRepository;

    @Autowired
    private MovieGenreService movieGenreService;

    public List<Movie> getAllMovies() {
        return moviesRepo.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return moviesRepo.findById(id);
    }

    public MovieResponseDTO getMovieResponseById(Long id) {

        Movie movie = moviesRepo.findById(id).get();
        List<String> genre = movieGenreService.getGenresByMovieId(id);

        return movieMapper.toMovieResponse(movie,genre);
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

    @Transactional
    public Movie addMovie(MovieRequestDTO movieRequest) {
            // 1. Convert DTO to Movies entity
            Movie movie = movieMapper.toMovie(movieRequest);
            Movie savedMovie = moviesRepo.save(movie);
            if (movieRequest.getGenre() != null && !movieRequest.getGenre().isEmpty()) {
                movieGenreService.saveGenresForMovie(savedMovie.getMovieId(), movieRequest.getGenre());
            }
            MovieSearch movieSearch = movieMapper.toMovieSearch(savedMovie.getMovieId(),movieRequest);
            movieSearchRepository.save(movieSearch);
            return savedMovie;
    }

    @Transactional
    public Movie updateMovie(Long id, MovieRequestDTO updatedMovieData) {

        Movie existingMovie = moviesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        Movie movie = movieMapper.toMovie(updatedMovieData);
        movie.setMovieId(id);
        movie.setCreatedAt(existingMovie.getCreatedAt());
        Movie savedMovie = moviesRepo.save(movie);

        movieGenreService.saveGenresForMovie(savedMovie.getMovieId(), updatedMovieData.getGenre());

        MovieSearch movieSearch = movieMapper.toMovieSearch(savedMovie.getMovieId(),updatedMovieData);
        movieSearchRepository.save(movieSearch);

        return moviesRepo.save(existingMovie);
    }


    @Transactional
    public void deleteMovieById(Long movieId) {
        // Delete genres first (to avoid FK constraint issues)
        movieGenreService.deleteGenresByMovieId(movieId);

        // Then delete the movie
        moviesRepo.deleteById(movieId);
        movieSearchRepository.deleteById(movieId);
    }

}
