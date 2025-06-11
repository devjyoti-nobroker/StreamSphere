package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.services.MovieService;
import com.nobroker.streamSphere.services.WatchHistoryService;
import com.nobroker.streamSphere.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recent")
public class RecentWatchController {

    @Autowired
    private UserContext userContext;

    @Autowired
    private WatchHistoryService watchHistoryService;

    @GetMapping("")
    public List<Movie> getRecentlyWatchedMovies(){
        return watchHistoryService.getRecentlyWatched(userContext.getProfileId());
    }
}
