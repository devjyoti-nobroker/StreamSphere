package com.nobroker.streamSphere.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSearchDTO {
    private Long movieId;
    private String movieName;
    private List<String> genre;
    private List<String> actorList;
    private String description;
    private String moviePoster;
    private String releaseDate;
    private Float rating;
    private String runTime;
}
