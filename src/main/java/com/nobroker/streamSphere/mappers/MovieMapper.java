package com.nobroker.streamSphere.mappers;

import com.nobroker.streamSphere.dtos.MovieCardDTO;
import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.dtos.MovieResponseDTO;
import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.models.MovieSearch;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Component
public class MovieMapper {

    public MovieCardDTO toMovieDTO(MovieCardProjection movieCardProjection){
        return new MovieCardDTO(movieCardProjection.getMovieId(),
                movieCardProjection.getMovieName(),
                movieCardProjection.getMoviePoster(),
                movieCardProjection.getReleaseDate().toString(),
                movieCardProjection.getRating()
        );
    }

    public MovieCardDTO toMovieDTO(MovieSearch movieSearch) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        return new MovieCardDTO(
                movieSearch.getId(),
                movieSearch.getTitle(),
                movieSearch.getImage(),
                movieSearch.getReleased(),
                movieSearch.getRating()
        );
    }

    public MovieSearch toMovieSearch(Long movieId,MovieRequestDTO movieRequest){
        return new MovieSearch(
                movieId,
                movieRequest.getMovieName(),
                movieRequest.getGenre(),
                movieRequest.getActorList(),
                movieRequest.getDescription(),
                movieRequest.getMoviePoster(),
                movieRequest.getReleaseDate().toString(),
                movieRequest.getRating()
        );
    }

    public Movie toMovie(MovieRequestDTO movieRequest){
        return new Movie(
                null,
                movieRequest.getMovieName(),
                movieRequest.getReleaseDate(),
                movieRequest.getRunTime(),
                movieRequest.getDescription(),
                movieRequest.getRating(),
                String.join(", ",movieRequest.getActorList()),
                movieRequest.getMoviePoster(),
                null,
                null,
                movieRequest.getUpdatedBy()
        );
    }

    public MovieResponseDTO toMovieResponse(Movie movie, List<String> genre){
        return  new MovieResponseDTO(
                movie.getMovieName(),
                movie.getReleaseDate(),
                movie.getRunTime(),
                movie.getDescription(),
                movie.getRating(),
                Arrays.asList(movie.getActorList().split(",\\s*")),
                movie.getMoviePoster(),
                genre
        );
    }
}
