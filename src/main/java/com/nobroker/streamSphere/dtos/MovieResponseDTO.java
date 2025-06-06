package com.nobroker.streamSphere.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDTO {
        private String movieName;
        private Date releaseDate;
        private String runTime;
        private String description;
        private float rating;
        private List<String> actorList;
        private String moviePoster;
        private List<String> genre; // not saved, just received
}
