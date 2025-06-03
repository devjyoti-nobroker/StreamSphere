package com.nobroker.streamSphere.projection;

import java.util.Date;

public interface MovieCardProjection {
    Long   getMovieId();
    String getMovieName();
    String getMoviePoster();
    Date getReleaseDate();
    Float getRating();
}