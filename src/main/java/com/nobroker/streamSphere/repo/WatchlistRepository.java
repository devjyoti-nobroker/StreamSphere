package com.nobroker.streamSphere.repo;

import com.nobroker.streamSphere.models.Watchlist;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    //Finds all watchlist entries (only movie ids) for a given profile in descending order of addedAt timestamp
    @Query("SELECT w.movieId FROM Watchlist w WHERE w.profileId = :profileId ORDER BY w.addedAt DESC")
    List<Long> findMovieIdsByProfileId(@Param("profileId") Long profileId);

    //Checks if a particular movie is already in the user's watchlist (to avoid duplicates)
    boolean existsByProfileIdAndMovieId(Long profileId, Long movieId);

    //Deletes a watchlist entry based on the profile and movie ID
    void deleteByProfileIdAndMovieId(Long profileId, Long movieId);

    //Finds total number of movies in watchlist
    Long countByProfileId(Long profileId);

    //Deletes the entire watchlist
    void deleteByProfileId(Long profileId);

}
