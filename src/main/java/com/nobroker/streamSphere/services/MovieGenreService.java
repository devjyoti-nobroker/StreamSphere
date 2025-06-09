package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.models.MovieGenre;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import com.nobroker.streamSphere.repositories.AccountRepo;
import com.nobroker.streamSphere.repositories.MovieGenreRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieGenreService {

    @Autowired

    private MovieGenreRepo repo;


    public Page<MovieCardProjection> getMovieCardsByGenre(String genre, Pageable pageable) {
        return repo.findMovieCardsByGenre(genre, pageable);
    }


    public List<String> getGenresByMovieId(Long movieId) {
        return repo.findGenresByMovieId(movieId);
    }

    @Transactional
    public void saveGenresForMovie(Long movieId, List<String> genres) {
        for (String genre : genres) {
            MovieGenre.MovieGenreId id = new MovieGenre.MovieGenreId(movieId, genre);
            MovieGenre movieGenre = new MovieGenre(id);
            repo.save(movieGenre);
        }
    }

    @Transactional
    public void deleteGenresByMovieId(Long movieId) {
        repo.deleteByIdMovieId(movieId);
    }




}
