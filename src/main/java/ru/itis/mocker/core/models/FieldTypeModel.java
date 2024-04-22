package ru.itis.mocker.core.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FieldTypeModel {
    private String type;
    private FieldTypeModel item;
}
