package ru.itis.mocker.core.objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FieldClassObject {
    private Boolean isBoolean;
    private Boolean isString;
    private Boolean isInteger;
    private Boolean isLong;
    private Boolean isDouble;
    private Boolean isList;
    private FieldTypeClassObject item;
    private String name;
    private String doc;
}
