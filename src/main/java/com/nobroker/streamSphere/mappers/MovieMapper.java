package com.nobroker.streamSphere.mappers;

import com.nobroker.streamSphere.dtos.MovieCardDTO;
import com.nobroker.streamSphere.dtos.MovieRequestDTO;
import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.models.MovieSearch;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieCardDTO toMovieDTO(MovieCardProjection movieCardProjection){
        return new MovieCardDTO(movieCardProjection.getMovieId(),
                movieCardProjection.getMovieName(),
                movieCardProjection.getMoviePoster(),
                movieCardProjection.getReleaseDate(),
                movieCardProjection.getRating()
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
                movieRequest.getReleaseDate(),
                movieRequest.getRating()
        );
    }

    public Movie toMovie(MovieRequestDTO movieRequest){
        Movie movie = new Movie();
        movie.setMovieName(movieRequest.getMovieName());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setRunTime(movieRequest.getRunTime());
        movie.setDescription(movieRequest.getDescription());
        movie.setRating(movieRequest.getRating());
        movie.setActorList(String.join(", ",movieRequest.getActorList()));
        movie.setMoviePoster(movieRequest.getMoviePoster());

        return movie;
    }
}
