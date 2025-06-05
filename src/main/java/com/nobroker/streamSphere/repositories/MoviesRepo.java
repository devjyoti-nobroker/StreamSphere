package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepo extends JpaRepository<Movie,Long> {

    // Sort by rating
    List<Movie> findAllByOrderByRatingDesc();
    List<Movie> findAllByOrderByRatingAsc();

    // Sort by release date
    List<Movie> findAllByOrderByReleaseDateDesc();
    List<Movie> findAllByOrderByReleaseDateAsc();



}
