package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.WatchHistoryDTO;
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
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        Movies movie = movieRepository.findById(movieId).orElseThrow();

        // If it already exists, remove and re-add to update watchedAt
        if (watchHistoryRepository.existsByIdProfileIdAndIdMovieId(profileId, movieId)) {
            watchHistoryRepository.deleteByIdProfileIdAndIdMovieId(profileId, movieId);
        }

        WatchHistory.WatchHistoryId id = new WatchHistory.WatchHistoryId(profileId, movieId);

        WatchHistory history = WatchHistory.builder()
                .id(id)
                .profile(profile)
                .movie(movie)
                .watchedAt(LocalDateTime.now())
                .build();

        return watchHistoryRepository.save(history);
    }

    // Get watch history of a user in descending order
    public List<WatchHistoryDTO> getWatchHistoryByProfile(Long profileId) {
        List<WatchHistory> historyList = watchHistoryRepository.findByProfileIdOrderByWatchedAtDesc(profileId);
        return historyList.stream().map(history ->
                WatchHistoryDTO.builder()
                        .profileId(history.getProfile().getId())
                        .movieId(history.getMovie().getMovieId())
                        .movieTitle(history.getMovie().getMovieName()) // Assuming there's a `title` field
                        .watchedAt(history.getWatchedAt())
                        .build()
        ).toList();
    }
//    public List<WatchHistory> getWatchHistoryByProfile(Long profileId) {
//        return watchHistoryRepository.findByProfileIdOrderByWatchedAtDesc(profileId);
//    }

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
