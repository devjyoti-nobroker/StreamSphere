package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.MovieCardDTO;
import com.nobroker.streamSphere.mappers.MovieMapper;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import com.nobroker.streamSphere.repositories.MovieSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequestMapping("api")
public class MovieSearchService {

    @Autowired
    private MovieSearchRepository movieSearchRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private MovieMapper movieMapper;

//    public  MovieSearch save()

    public List<MovieCardDTO> searchByText(String text) throws IOException {
        Criteria criteria = new Criteria()
                .or(new Criteria("title").contains(text))
                .or(new Criteria("genre").contains(text))
                .or(new Criteria("actors").contains(text))
                .or(new Criteria("released").is(text)); // released is a Date; consider formatting if needed

        Query query = new CriteriaQuery(criteria);

        SearchHits<MovieCardProjection> searchHits = elasticsearchOperations.search(
                query,
                MovieCardProjection.class,
                IndexCoordinates.of("movies")
        );

        List<MovieCardDTO> resultList = new ArrayList<>();
        for (SearchHit<MovieCardProjection> hit : searchHits) {
            MovieCardDTO movieCardDTO = movieMapper.toMovieDTO(hit.getContent());
            resultList.add(movieCardDTO);
        }

        return resultList;
    }
}
