package com.nobroker.streamSphere.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCardDTO {

    private Long movieId;
    private String movieName;
    private String moviePoster;
    private Date releaseDate;
    private Float rating;

}