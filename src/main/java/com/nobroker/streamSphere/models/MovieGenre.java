package com.nobroker.streamSphere.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="movie_genre")

public class MovieGenre {

    @EmbeddedId
    private MovieGenreId id;


    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieGenreId implements Serializable {

        @Column(name="movie_id")
        private Long movieId;

        @Column(name="genre")
        private String genre;
    }
}
