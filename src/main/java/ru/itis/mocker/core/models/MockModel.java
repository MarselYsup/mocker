package ru.itis.mocker.core.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MockModel {
    private String name;
    private RequestModel request;
    private ResponseModel response;
}
