package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.WatchHistoryDTO;
import com.nobroker.streamSphere.exception.MovieNotFoundException;
import com.nobroker.streamSphere.exception.ProfileNotFoundException;
import com.nobroker.streamSphere.mappers.WatchHistoryMapper;
import com.nobroker.streamSphere.models.Movies;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.models.WatchHistory;
import com.nobroker.streamSphere.repositories.MoviesRepo;
import com.nobroker.streamSphere.repositories.ProfileRepo;
import com.nobroker.streamSphere.repositories.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final ProfileRepo profileRepository;
    private final MoviesRepo movieRepository;

    // Add a movie to watch history
    @Transactional
    public WatchHistory addToHistory(Long profileId, Long movieId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        Movies movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        WatchHistory history;

        if (watchHistoryRepository.existsByIdProfileIdAndIdMovieId(profileId, movieId)) {
            history = watchHistoryRepository.findByIdProfileIdAndIdMovieId(profileId, movieId).get();
            history.setWatchedAt(LocalDateTime.now());
        } else {
            WatchHistory.WatchHistoryId id = new WatchHistory.WatchHistoryId(profileId, movieId);

            history = WatchHistory.builder()
                    .id(id)
                    .profile(profile)
                    .movie(movie)
                    .watchedAt(LocalDateTime.now())
                    .build();
        }

        return watchHistoryRepository.save(history);
    }


    // Get watch history of a user in descending order
    public List<WatchHistoryDTO> getWatchHistoryByProfile(Long profileId) {
        if (!profileRepository.existsById(profileId)) {
            throw new ProfileNotFoundException(profileId);
        }

        List<WatchHistory> historyList = watchHistoryRepository.findByProfileIdOrderByWatchedAtDesc(profileId);

        return historyList.stream()
                .map(WatchHistoryMapper::toDTO)
                .toList();
    }


    // Check if a movie is in watch history
    public boolean isMovieWatched(Long profileId, Long movieId) {
        return watchHistoryRepository.existsByIdProfileIdAndIdMovieId(profileId, movieId);
    }

    // Remove a specific movie from watch history
    @Transactional
    public void removeFromHistory(Long profileId, Long movieId) {
        watchHistoryRepository.deleteByIdProfileIdAndIdMovieId(profileId, movieId);
    }
}
