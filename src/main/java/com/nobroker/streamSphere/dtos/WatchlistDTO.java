package com.nobroker.streamSphere.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistDTO {
    private Long profileId;
    private Long movieId;
    private String movieTitle;
    private LocalDateTime addedAt;
}
