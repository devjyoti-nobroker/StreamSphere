package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.MovieSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieSearchRepository extends ElasticsearchRepository<MovieSearch,Long> {
}
