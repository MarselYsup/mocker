package ru.itis.mocker.core.objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HttpHeaderObject {
    private String name;
    private String value;
}
