package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.WatchlistDTO;
import com.nobroker.streamSphere.exception.ProfileNotFoundException;
import com.nobroker.streamSphere.exception.ServiceException;
import com.nobroker.streamSphere.models.Movies;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.models.Watchlist;
import com.nobroker.streamSphere.models.Watchlist.WatchListId;
import com.nobroker.streamSphere.repositories.MoviesRepo;
import com.nobroker.streamSphere.repositories.ProfileRepo;
import com.nobroker.streamSphere.repositories.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final ProfileRepo profileRepository;
    private final MoviesRepo movieRepository;

    // Adds a movie to the watchlist if it's not already present
    public void addToWatchlist(Long profileId, Long movieId) {
        try {
            if (!watchlistRepository.existsByIdProfileIdAndIdMovieId(profileId, movieId)) {
                Profile profile = profileRepository.findById(profileId)
                        .orElseThrow(() -> new ProfileNotFoundException(profileId));
                Movies movie = movieRepository.findById(movieId)
                        .orElseThrow(() -> new ProfileNotFoundException(movieId));

                Watchlist entry = Watchlist.builder()
                        .id(new WatchListId(profileId, movieId))
                        .profile(profile)
                        .movie(movie)
                        .addedAt(LocalDateTime.now())
                        .build();

                watchlistRepository.save(entry);
            }
        } catch (Exception e) {
            throw new ServiceException("Error while adding to watchlist", e);
        }
    }

    // Removes a movie from the watchlist
    @Transactional
    public void removeFromWatchlist(Long profileId, Long movieId) {
        try {
            watchlistRepository.deleteByIdProfileIdAndIdMovieId(profileId, movieId);
        } catch (Exception e) {
            throw new ServiceException("Error while removing from watchlist", e);
        }
    }

    // Gets movie IDs in descending order of addition time
    public List<WatchlistDTO> getWatchlistMovieIds(Long profileId) {
        try {
            List<Watchlist> watchlist = watchlistRepository.findByProfileIdOrderByAddedAtDesc(profileId);
            return watchlist.stream().map(item ->
                    WatchlistDTO.builder()
                            .profileId(item.getProfile().getId())
                            .movieId(item.getMovie().getMovieId())
                            .movieTitle(item.getMovie().getMovieName())
                            .addedAt(item.getAddedAt())
                            .build()
            ).toList();
        } catch (Exception e) {
            throw new ServiceException("Error while fetching watchlist", e);
        }
    }

    // Checks if a movie exists in the watchlist
    public boolean isInWatchlist(Long profileId, Long movieId) {
        try {
            return watchlistRepository.existsByIdProfileIdAndIdMovieId(profileId, movieId);
        } catch (Exception e) {
            throw new ServiceException("Error while checking watchlist status", e);
        }
    }

    // Gets total count of movies in watchlist
    public Long getWatchlistCount(Long profileId) {
        try {
            return watchlistRepository.countByProfileId(profileId);
        } catch (Exception e) {
            throw new ServiceException("Error while counting watchlist entries", e);
        }
    }

    // Clears the entire watchlist for a given profile
    @Transactional
    public void clearWatchlist(Long profileId) {
        try {
            watchlistRepository.deleteByIdProfileId(profileId);
        } catch (Exception e) {
            throw new ServiceException("Error while clearing watchlist", e);
        }
    }

}
