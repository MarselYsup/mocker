package ru.itis.mocker.core.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QueryParamModel {
    private String name;
    private String type;
    private Boolean required;
}
