package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.models.WatchHistory;
import com.nobroker.streamSphere.repositories.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;

    // Add a movie to watch history
    public WatchHistory addToHistory(Long profileId, Long movieId) {
        // If it already exists, remove and re-add to update watchedAt
        if (watchHistoryRepository.existsByProfileIdAndMovieId(profileId, movieId)) {
            watchHistoryRepository.deleteByProfileIdAndMovieId(profileId, movieId);
        }

        WatchHistory history = WatchHistory.builder()
                .profileId(profileId)
                .movieId(movieId)
                .watchedAt(LocalDateTime.now())
                .build();

        return watchHistoryRepository.save(history);
    }

    // Get watch history of a user in descending order
    public List<WatchHistory> getWatchHistoryByProfile(Long profileId) {
        return watchHistoryRepository.findByProfileIdOrderByWatchedAtDesc(profileId);
    }

    // Check if a movie is in watch history
    public boolean isMovieWatched(Long profileId, Long movieId) {
        return watchHistoryRepository.existsByProfileIdAndMovieId(profileId, movieId);
    }

    // Remove a specific movie from watch history
    public void removeFromHistory(Long profileId, Long movieId) {
        watchHistoryRepository.deleteByProfileIdAndMovieId(profileId, movieId);
    }
}
