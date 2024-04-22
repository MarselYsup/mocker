package ru.itis.mocker.core.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ClassModel {
    private String name;
    private List<FieldModel> fields;
}
