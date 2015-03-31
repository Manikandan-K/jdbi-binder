package com.github.rkmk.binder.model;

import com.github.rkmk.binder.BindObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Movie {
    private Integer movieId;
    private String movieName;
    @BindObject("d")
    private Director director;

    private BigDecimal ratings;

}
