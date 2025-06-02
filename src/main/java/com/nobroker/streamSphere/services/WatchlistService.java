package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.models.Watchlist;
import com.nobroker.streamSphere.repositories.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;

    // Adds a movie to the watchlist if it's not already present
    public void addToWatchlist(Long profileId, Long movieId) {
        if (!watchlistRepository.existsByProfileIdAndMovieId(profileId, movieId)) {
            Watchlist entry = Watchlist.builder()
                    .profileId(profileId)
                    .movieId(movieId)
                    .addedAt(LocalDateTime.now())
                    .build();
            watchlistRepository.save(entry);
        }
    }

    // Removes a movie from the watchlist
    public void removeFromWatchlist(Long profileId, Long movieId) {
        watchlistRepository.deleteByProfileIdAndMovieId(profileId, movieId);
    }

    // Gets movie IDs in descending order of addition time
    public List<Long> getWatchlistMovieIds(Long profileId) {
        return watchlistRepository.findMovieIdsByProfileId(profileId);
    }

    // Checks if a movie exists in the watchlist
    public boolean isInWatchlist(Long profileId, Long movieId) {
        return watchlistRepository.existsByProfileIdAndMovieId(profileId, movieId);
    }

    // Gets total count of movies in watchlist
    public Long getWatchlistCount(Long profileId) {
        return watchlistRepository.countByProfileId(profileId);
    }

    // Clears the entire watchlist
    public void clearWatchlist(Long profileId) {
        watchlistRepository.deleteByProfileId(profileId);
    }
}
