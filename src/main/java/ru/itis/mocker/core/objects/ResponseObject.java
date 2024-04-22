package ru.itis.mocker.core.objects;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseObject {
    private Boolean AreExistedHeaders;
    private Boolean isExistedBody;
    private Integer status;
    private List<HttpHeaderObject> headers;
    private BodyObject body;
}
