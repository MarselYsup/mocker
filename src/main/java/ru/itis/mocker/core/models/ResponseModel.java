package ru.itis.mocker.core.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseModel {
    private Integer status;
    private List<HttpHeaderModel> headers;
    private String body;
    private String bodyRef;
}
