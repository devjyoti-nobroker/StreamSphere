package com.nobroker.streamSphere.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "watch_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Watchlist {

    @EmbeddedId
    private WatchListId id;

    @Column(name = "time_stamp")
    private LocalDateTime addedAt;

    @MapsId("profileId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @MapsId("movieId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @PrePersist
    public void prePersist() {
        this.addedAt = LocalDateTime.now();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class WatchListId implements Serializable {
        private Long profileId;
        private Long movieId;
    }
}
