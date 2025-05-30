package com.nobroker.streamSphere.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long profileId;
    private Long movieId;
    private LocalDateTime watchedAt;

    @PrePersist
    public void prePersist() {
        this.watchedAt = LocalDateTime.now();
    }
}
