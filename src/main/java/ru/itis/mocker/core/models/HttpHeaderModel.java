package ru.itis.mocker.core.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HttpHeaderModel {
    private String name;
    private String value;
}
