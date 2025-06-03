package com.nobroker.streamSphere.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MovieRequestDTO {
    private String movieName;
    private Date releaseDate;
    private String runTime;
    private String description;
    private float rating;
    private String actorList;
    private String moviePoster;
    private Date createdAt;
    private Date updatedAt;
    private Long updatedBy;
    private List<String> genre; // not saved, just received
}
