package com.nobroker.streamSphere.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSearchDTO {
    Long   movieId;
    String movieName;
    String moviePoster;
    String releaseDate;
    Float rating;
}
