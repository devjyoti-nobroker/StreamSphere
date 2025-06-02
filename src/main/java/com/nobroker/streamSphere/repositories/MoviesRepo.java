package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepo extends JpaRepository<Movies,Long> {

    // Sort by rating
    List<Movies> findAllByOrderByRatingDesc();
    List<Movies> findAllByOrderByRatingAsc();

    // Sort by release date
    List<Movies> findAllByOrderByReleaseDateDesc();
    List<Movies> findAllByOrderByReleaseDateAsc();



}
