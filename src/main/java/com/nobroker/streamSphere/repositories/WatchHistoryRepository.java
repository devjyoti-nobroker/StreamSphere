package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    //Finds all watch history entries for a particular profile in descending order of watchedAt timestamp
    List<WatchHistory> findByProfileIdOrderByWatchedAtDesc(Long profileId);

    //Find if a movie was already watched by a profile
    boolean existsByProfileIdAndMovieId(Long profileId, Long movieId);

    //Delete watch history for a specific movie of a profile
    void deleteByProfileIdAndMovieId(Long profileId, Long movieId);

}
