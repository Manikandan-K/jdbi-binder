package com.github.rkmk.binder.model;

import com.github.rkmk.binder.BindObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;
import org.skife.jdbi.v2.sqlobject.Bind;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MovieWithOutNameSpace {
    private Integer movieId;
    private String movieName;
    @BindObject
    private Director director;

    private BigDecimal ratings;

}
