package com.nobroker.streamSphere.service;

import com.nobroker.streamSphere.models.Movies;
import com.nobroker.streamSphere.repo.MoviesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class MovieService {


    @Autowired
    private MoviesRepo moviesRepo;


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

    public Movies addMovie(Movies movie, MultipartFile imageFile) throws IOException {
        movie.setMoviePoster(imageFile.getOriginalFilename());
        movie.setImageType(imageFile.getContentType());
        movie.setImageData(imageFile.getBytes());
        movie.setCreatedAt(new Date());
        movie.setUpdatedAt(new Date());
        return moviesRepo.save(movie);
    }




    public Movies updateMovie(Long id, Movies updatedMovieData, MultipartFile imageFile) throws IOException {
        // 1. Fetch existing movie
        Movies existingMovie = moviesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        // 2. Update fields only if new values are provided
        if (updatedMovieData.getMovieName() != null)
            existingMovie.setMovieName(updatedMovieData.getMovieName());

        if (updatedMovieData.getDescription() != null)
            existingMovie.setDescription(updatedMovieData.getDescription());

        if (updatedMovieData.getActorList() != null)
            existingMovie.setActorList(updatedMovieData.getActorList());

        if (updatedMovieData.getRating() != 0)
            existingMovie.setRating(updatedMovieData.getRating());

        if (updatedMovieData.getReleaseDate() != null)
            existingMovie.setReleaseDate(updatedMovieData.getReleaseDate());

        if (updatedMovieData.getRunTime() != null)
            existingMovie.setRunTime(updatedMovieData.getRunTime());

        if (updatedMovieData.getUpdatedBy() != null)
            existingMovie.setUpdatedBy(updatedMovieData.getUpdatedBy());

        existingMovie.setUpdatedAt(new Date());

        // 3. If a new image file is provided
        if (imageFile != null && !imageFile.isEmpty()) {
            existingMovie.setMoviePoster(imageFile.getOriginalFilename());
            existingMovie.setImageType(imageFile.getContentType());
            existingMovie.setImageData(imageFile.getBytes());
        }

        // 4. Save and return
        return moviesRepo.save(existingMovie);
    }


}
