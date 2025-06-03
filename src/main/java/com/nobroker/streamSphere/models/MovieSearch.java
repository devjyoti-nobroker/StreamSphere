package com.nobroker.streamSphere.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.List;

@Document(indexName = "movies")
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
//
//"id": {"type": "long" },
//        "title":    { "type": "text" },
//        "genre":    { "type": "keyword" },
//        "actors":     { "type": "keyword" },
//        "description":   { "type": "text" },
//        "image":   { "type": "text", "index": false },
//        "released":   { "type": "date" },
//        "rating":   { "type": "float", "index": false }
//