package ru.itis.mocker.core.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FieldModel {
    private String name;
    private String type;
    private String doc;
    // only if you use list
    private FieldTypeModel item;
}
