package com.nobroker.streamSphere.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document(indexName = "movies")
@Data
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
    private String released;
    private Float rating;
    private String runtime;
}