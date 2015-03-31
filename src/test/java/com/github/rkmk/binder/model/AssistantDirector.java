package com.github.rkmk.binder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AssistantDirector {
    private Integer id;
    private String name;
    private Integer directorId;
}
