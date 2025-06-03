package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.models.Watchlist;
import com.nobroker.streamSphere.services.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    // Add to watchlist
    @PostMapping("/{profileId}/{movieId}")
    public ResponseEntity<String> addToWatchlist(@PathVariable Long profileId, @PathVariable Long movieId) {
        watchlistService.addToWatchlist(profileId, movieId);
        return ResponseEntity.ok("Movie added to watchlist.");
    }

    // Get all movie IDs in watchlist
    @GetMapping("/{profileId}")
    public ResponseEntity<List<Watchlist>> getWatchlist(@PathVariable Long profileId) {
        return ResponseEntity.ok(watchlistService.getWatchlistMovieIds(profileId));
    }

    // Remove movie from watchlist
    @DeleteMapping("/{profileId}/{movieId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Long profileId, @PathVariable Long movieId) {
        watchlistService.removeFromWatchlist(profileId, movieId);
        return ResponseEntity.ok("Movie removed from watchlist.");
    }

    // Get count of movies in watchlist
    @GetMapping("/{profileId}/count")
    public ResponseEntity<Long> getWatchlistCount(@PathVariable Long profileId) {
        return ResponseEntity.ok(watchlistService.getWatchlistCount(profileId));
    }

    // Clear entire watchlist
    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> clearWatchlist(@PathVariable Long profileId) {
        watchlistService.clearWatchlist(profileId);
        return ResponseEntity.ok("Watchlist cleared.");
    }
}
