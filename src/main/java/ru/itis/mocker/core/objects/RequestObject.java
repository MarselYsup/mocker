package ru.itis.mocker.core.objects;

import lombok.*;
import ru.itis.mocker.core.models.enums.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RequestObject {
    private RequestMethod method;
    private String path;
    private List<QueryParamObject> queryParams;
}
