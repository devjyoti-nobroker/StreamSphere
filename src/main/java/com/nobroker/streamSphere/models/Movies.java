package com.nobroker.streamSphere.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="movies")
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long movieId;

    @Column(name="title")
    private String movieName;

    @Column(name="released")
    private Date releaseDate;

    @Column(name="runtime")
    private String runTime;

    @Column(name="description")
    private String description;

    @Column(name="rating")
    private float rating;

    @Column(name="actors")
    private String actorList;

    @Column(name="image")
    private String moviePoster;


    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name="updated_by")
    private Long updatedBy;

}
