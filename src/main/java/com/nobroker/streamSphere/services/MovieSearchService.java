package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.MovieCardDTO;
import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.dtos.MovieResponseDTO;
import com.nobroker.streamSphere.dtos.MovieSearchDTO;
import com.nobroker.streamSphere.exception.MissingSearchParameterException;
import com.nobroker.streamSphere.mappers.MovieMapper;
import com.nobroker.streamSphere.models.MovieSearch;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import com.nobroker.streamSphere.repositories.MovieSearchRepository;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
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
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequestMapping("api")
public class MovieSearchService {

    @Autowired
    private MovieSearchRepository movieSearchRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private RestHighLevelClient client;

    public MovieSearch save(MovieSearch movieSearch){
        return movieSearchRepository.save(movieSearch);
    }

    public void remove(Long movieId){
        movieSearchRepository.deleteById(movieId);
    }

    public List<MovieCardDTO> getAllMovies() throws ParseException {
        Iterable<MovieSearch> allMovies = movieSearchRepository.findAll();
        List<MovieCardDTO> resultList = new ArrayList<>();
        for (MovieSearch movie : allMovies) {
            MovieCardDTO dto = movieMapper.toMovieDTO(movie);
            resultList.add(dto);
        }
        return resultList;
    }


    public List<MovieSearchDTO> searchByTextGenreAndRating(
            String text,
            String genre,
            Float minRating,
            Float maxRating) throws IOException, ParseException {

        if(text==null && genre==null && minRating==null && maxRating==null){
            throw new MissingSearchParameterException("At least one search parameter (q, genre, ratingMin, ratingMax) must be provided");
        }

        // Split the input text into individual words
        String[] words = text.trim().split("\\s+");

        // Title criteria - partial match on words in title
        Criteria titleCriteria = null;
        for (String word : words) {
            Criteria wordCriteria = new Criteria("title").contains(word);
            if (titleCriteria == null) {
                titleCriteria = wordCriteria;
            } else {
                titleCriteria = titleCriteria.and(wordCriteria);
            }
        }

        // Genre criteria - exact match
        Criteria genreCriteria = null;
        if (genre != null && !genre.isEmpty()) {
            genreCriteria = new Criteria("genre").contains(genre);
        }

        // Rating range criteria
        Criteria ratingCriteria = null;
        if (minRating != null && maxRating != null) {
            ratingCriteria = new Criteria("rating").between(minRating, maxRating);
        } else if (minRating != null) {
            ratingCriteria = new Criteria("rating").greaterThanEqual(minRating);
        } else if (maxRating != null) {
            ratingCriteria = new Criteria("rating").lessThanEqual(maxRating);
        }

        // Combine all criteria with AND logic
        Criteria combinedCriteria = titleCriteria;

        if (genreCriteria != null) {
            combinedCriteria = combinedCriteria == null ? genreCriteria : combinedCriteria.and(genreCriteria);
        }
        if (ratingCriteria != null) {
            combinedCriteria = combinedCriteria == null ? ratingCriteria : combinedCriteria.and(ratingCriteria);
        }

        // Optionally match entire input as a released date OR with existing criteria
        if (text.matches("\\d{4}-\\d{2}-\\d{2}")) {
            Criteria releaseCriteria = new Criteria("released").is(text);
            combinedCriteria = combinedCriteria == null ? releaseCriteria : combinedCriteria.or(releaseCriteria);
        }

        Query query = new CriteriaQuery(combinedCriteria);

        SearchHits<MovieSearch> searchHits = elasticsearchOperations.search(
                query,
                MovieSearch.class,
                IndexCoordinates.of("movies")
        );

        List<MovieSearchDTO> resultList = new ArrayList<>();
        for (SearchHit<MovieSearch> hit : searchHits) {
            MovieSearchDTO movieSearchDTO = movieMapper.toMovieSearchDTO(hit.getContent());
            resultList.add(movieSearchDTO);
        }

        return resultList;
    }


    public List<String> autocomplete(String prefix) throws IOException {
        SearchRequest searchRequest = new SearchRequest("movies");

        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders
                .completionSuggestion("suggest")
                .prefix(prefix)
                .skipDuplicates(true)
                .size(10);

        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("movie-suggest", suggestionBuilder);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().suggest(suggestBuilder);
        searchRequest.source(sourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        List<String> suggestions = new ArrayList<>();
        CompletionSuggestion suggestion = response.getSuggest().getSuggestion("movie-suggest");

        for (CompletionSuggestion.Entry entry : suggestion.getEntries()) {
            for (CompletionSuggestion.Entry.Option option : entry) {
                suggestions.add(option.getText().string());
            }
        }

        return suggestions;
    }



}
