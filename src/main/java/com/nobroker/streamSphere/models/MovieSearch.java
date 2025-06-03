package com.nobroker.streamSphere.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.List;

@Document(indexName = "movies")
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearch {
    @Id
    private Long id;
    private String title;
    private List<String> genre;
    private List<String> actors;
    private String description;
    private String image;
    private Date released;
    private Float rating;
}