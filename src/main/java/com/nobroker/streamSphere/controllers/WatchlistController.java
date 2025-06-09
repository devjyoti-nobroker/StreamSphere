package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.WatchlistDTO;
import com.nobroker.streamSphere.models.Watchlist;
import com.nobroker.streamSphere.services.WatchlistService;
import com.nobroker.streamSphere.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @Autowired
    private UserContext userContext;

    // Add to watchlist
    @PostMapping("/{movieId}")
    public ResponseEntity<String> addToWatchlist(@PathVariable Long movieId) {
        Long profileId = userContext.getProfileId();
        watchlistService.addToWatchlist(profileId, movieId);
        return ResponseEntity.ok("Movie added to watchlist.");
    }

    // Get all movie IDs in watchlist
    @GetMapping("")
    public ResponseEntity<List<WatchlistDTO>> getWatchlist() {
        Long profileId = userContext.getProfileId();
        return ResponseEntity.ok(watchlistService.getWatchlistMovieIds(profileId));
    }

    // Remove movie from watchlist
    @DeleteMapping("{movieId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Long movieId) {
        Long profileId = userContext.getProfileId();
        watchlistService.removeFromWatchlist(profileId, movieId);
        return ResponseEntity.ok("Movie removed from watchlist.");
    }

    // Check if a movie is in watch history
    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> isWatched(@PathVariable Long movieId) {
        Long profileId = userContext.getProfileId();
        return ResponseEntity.ok(watchlistService.isInWatchlist(profileId, movieId));
    }

    // Get count of movies in watchlist
    @GetMapping("/count")
    public ResponseEntity<Long> getWatchlistCount() {
        Long profileId = userContext.getProfileId();
        return ResponseEntity.ok(watchlistService.getWatchlistCount(profileId));
    }

    // Clear entire watchlist
    @DeleteMapping("")
    public ResponseEntity<String> clearWatchlist() {
        Long profileId = userContext.getProfileId();
        watchlistService.clearWatchlist(profileId);
        return ResponseEntity.ok("Watchlist cleared.");
    }
}