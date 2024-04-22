package ru.itis.mocker.core.objects;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BodyObject {
    private String name;
    private List<FieldObject> fields;
    private String bodyJson;
}
