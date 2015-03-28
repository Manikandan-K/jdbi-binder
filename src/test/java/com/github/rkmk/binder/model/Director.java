package com.github.rkmk.binder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Director {
    private Integer directorId;
    private String directorName;
}
