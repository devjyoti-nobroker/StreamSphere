package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.Movie;
import com.nobroker.streamSphere.models.MovieGenre;
import com.nobroker.streamSphere.projection.MovieCardProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieGenreRepo extends JpaRepository<MovieGenre, MovieGenre.MovieGenreId> {


    //Query is mainly written to join the Genre and movie table, and then we get only those columns that are required
    @Query(value = """
        SELECT  m.id   AS movieId,
                m.title AS movieName,
                m.image AS moviePoster,
                m.rating    AS rating,
                m.released  AS releaseDate
        FROM movies m
        INNER JOIN movie_genre mg ON mg.movie_id = m.id
        WHERE mg.genre = :genre
        """, nativeQuery = true)
    List<MovieCardProjection> findMovieCardsByGenre(@Param("genre") String genre);

    //Query to get genres of a movie
    @Query("SELECT mg.id.genre FROM MovieGenre mg WHERE mg.id.movieId = :movieId")
    List<String> findGenresByMovieId(@Param("movieId") Long movieId);


    //Query to delete a movie by id
    void deleteByIdMovieId(Long movieId);



}
