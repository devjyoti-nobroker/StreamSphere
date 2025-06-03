package com.nobroker.streamSphere.mappers;

import com.nobroker.streamSphere.dtos.WatchlistDTO;
import com.nobroker.streamSphere.models.Watchlist;

public class WatchlistMapper {

    public static WatchlistDTO toDTO(Watchlist item) {
        return WatchlistDTO.builder()
                .profileId(item.getProfile().getId())
                .movieId(item.getMovie().getMovieId())
                .movieTitle(item.getMovie().getMovieName())
                .addedAt(item.getAddedAt())
                .build();
    }
}

