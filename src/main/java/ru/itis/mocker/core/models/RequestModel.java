package ru.itis.mocker.core.models;

import lombok.*;
import ru.itis.mocker.core.models.enums.RequestMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RequestModel {
    private RequestMethod method;
    private String path;
    private List<QueryParamModel> queryParams;
}
