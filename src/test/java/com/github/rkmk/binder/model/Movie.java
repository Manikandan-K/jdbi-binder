package com.github.rkmk.binder.model;

import com.github.rkmk.binder.BindObject;
import com.github.rkmk.binder.Property;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    private Integer movieId;
    private String movieName;

    @BindObject("d")
    private Director director;

    private BigDecimal ratings;

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public Director getDirector() {
        return director;
    }

    public BigDecimal getRatings() {
        return ratings;
    }
}
