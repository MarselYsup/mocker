package ru.itis.mocker.core.objects;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QueryParamObject {
    private Boolean isBoolean;
    private Boolean isString;
    private Boolean isInteger;
    private Boolean isLong;
    private Boolean isDouble;
    private String name;
    private Boolean required;
    private Boolean isLast;
}
