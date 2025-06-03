package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    //Finds all watchlist entries for a given profile in descending order of addedAt timestamp
    List<Watchlist> findByIdProfileIdOrderByAddedAtDesc(Long profileId);

    //Checks if a particular movie is already in the user's watchlist (to avoid duplicates)
    boolean existsByIdProfileIdAndIdMovieId(Long profileId, Long movieId);

    //Deletes a watchlist entry based on the profile and movie ID
    void deleteByIdProfileIdAndIdMovieId(Long profileId, Long movieId);

    //Finds total number of movies in watchlist
    Long countByProfileId(Long profileId);

    //Clears the watchlist for a profile
    void deleteByIdProfileId(Long profileId);

}
