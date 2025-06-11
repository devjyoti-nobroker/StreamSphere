package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepo extends JpaRepository<Movie,Long> {

    // Sort by rating
    Page<Movie> findAllByOrderByRatingDesc(Pageable pageable);
    Page<Movie> findAllByOrderByRatingAsc(Pageable pageable);

    // Sort by release date
    Page<Movie> findAllByOrderByReleaseDateDesc(Pageable pageable);
    Page<Movie> findAllByOrderByReleaseDateAsc(Pageable pageable);



}
