package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.WatchHistoryDTO;
import com.nobroker.streamSphere.models.WatchHistory;
import com.nobroker.streamSphere.services.WatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    // Add to watch history
    @PostMapping("/{movieId}")
    public ResponseEntity<String> addToHistory(@PathVariable Long profileId, @PathVariable Long movieId) {
        watchHistoryService.addToHistory(profileId, movieId);
        return ResponseEntity.ok("Movie added to watch history.");
    }

    // Get full watch history for a profile
    @GetMapping("")
    public ResponseEntity<List<WatchHistoryDTO>> getWatchHistory(@PathVariable Long profileId) {
        return ResponseEntity.ok(watchHistoryService.getWatchHistoryByProfile(profileId));
    }

    // Check if a movie is in watch history
    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> isWatched(@PathVariable Long profileId, @PathVariable Long movieId) {
        return ResponseEntity.ok(watchHistoryService.isMovieWatched(profileId, movieId));
    }

    // Remove a movie from watch history
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> removeFromHistory(@PathVariable Long profileId, @PathVariable Long movieId) {
        watchHistoryService.removeFromHistory(profileId, movieId);
        return ResponseEntity.ok("Movie removed from watch history.");
    }
}
