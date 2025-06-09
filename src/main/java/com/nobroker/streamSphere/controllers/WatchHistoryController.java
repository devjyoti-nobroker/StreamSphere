package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.WatchHistoryDTO;
import com.nobroker.streamSphere.models.WatchHistory;
import com.nobroker.streamSphere.security.JwtUtil;
import com.nobroker.streamSphere.services.WatchHistoryService;
import com.nobroker.streamSphere.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @Autowired
    private UserContext userContext;

    // Add to watch history
    @PostMapping("/{movieId}")
    public ResponseEntity<String> addToHistory(@PathVariable Long movieId) {
        Long profileId = userContext.getProfileId();
        watchHistoryService.addToHistory(profileId, movieId);
        return ResponseEntity.ok("Movie added to watch history.");
    }

    // Get full watch history for a profile
    @GetMapping("")
    public ResponseEntity<List<WatchHistoryDTO>> getWatchHistory() {
        Long profileId = userContext.getProfileId();
        return ResponseEntity.ok(watchHistoryService.getWatchHistoryByProfile(profileId));
    }

    // Check if a movie is in watch history
    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> isWatched(@PathVariable Long movieId) {
        Long profileId = userContext.getProfileId();
        return ResponseEntity.ok(watchHistoryService.isMovieWatched(profileId, movieId));
    }

    // Remove a movie from watch history
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> removeFromHistory(@PathVariable Long movieId) {
        Long profileId = userContext.getProfileId();
        watchHistoryService.removeFromHistory(profileId, movieId);
        return ResponseEntity.ok("Movie removed from watch history.");
    }
}
