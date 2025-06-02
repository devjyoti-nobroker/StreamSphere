package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.models.Movies;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.models.WatchHistory;
import com.nobroker.streamSphere.repositories.MoviesRepo;
import com.nobroker.streamSphere.repositories.ProfileRepo;
import com.nobroker.streamSphere.repositories.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final ProfileRepo profileRepository;
    private final MoviesRepo movieRepository;

    // Add a movie to watch history
    public WatchHistory addToHistory(Long profileId, Long movieId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        Movies movie = movieRepository.findById(movieId).orElseThrow();

        // If it already exists, remove and re-add to update watchedAt
        if (watchHistoryRepository.existsByProfileIdAndMovieId(profileId, movieId)) {
            watchHistoryRepository.deleteByProfileIdAndMovieId(profileId, movieId);
        }

        WatchHistory history = WatchHistory.builder()
                .profile(profile)
                .movie(movie)
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
