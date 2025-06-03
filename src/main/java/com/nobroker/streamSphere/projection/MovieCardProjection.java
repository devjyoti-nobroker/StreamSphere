package com.nobroker.streamSphere.projection;

import java.util.Date;

//Only important attributes to send it to frontend


public interface MovieCardProjection {
    Long   getMovieId();
    String getMovieName();
    String getMoviePoster();
    Date getReleaseDate();
    Float getRating();
}