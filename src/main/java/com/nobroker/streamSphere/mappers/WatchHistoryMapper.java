package com.nobroker.streamSphere.mappers;

import com.nobroker.streamSphere.dtos.WatchHistoryDTO;
import com.nobroker.streamSphere.models.WatchHistory;

public class WatchHistoryMapper {

    public static WatchHistoryDTO toDTO(WatchHistory history) {
        return WatchHistoryDTO.builder()
                .profileId(history.getProfile().getId())
                .movieId(history.getMovie().getMovieId())
                .movieTitle(history.getMovie().getMovieName())
                .watchedAt(history.getWatchedAt())
                .build();
    }
}

