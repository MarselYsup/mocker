package ru.itis.mocker.core.objects;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ClassObject {
    private String name;
    private List<FieldClassObject> fields;
    private String groupId;
    private String artefactId;
}
