package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.models.Movies;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.models.Watchlist;
import com.nobroker.streamSphere.models.Watchlist.WatchListId;
import com.nobroker.streamSphere.repositories.MovieRepository;
import com.nobroker.streamSphere.repositories.ProfileRepo;
import com.nobroker.streamSphere.repositories.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final ProfileRepo profileRepository;
    private final MovieRepository movieRepository;

    // Adds a movie to the watchlist if it's not already present
    public void addToWatchlist(Long profileId, Long movieId) {
        if (!watchlistRepository.existsByProfileIdAndMovieId(profileId, movieId)) {
            Profile profile = profileRepository.findById(profileId).orElseThrow();
            Movies movie = movieRepository.findById(movieId).orElseThrow();

            Watchlist entry = Watchlist.builder()
                    .id(new WatchListId(profileId, movieId))
                    .profile(profile)
                    .movie(movie)
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
}