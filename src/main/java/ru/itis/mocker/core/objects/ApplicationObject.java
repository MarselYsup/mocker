package ru.itis.mocker.core.objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApplicationObject {
    private String groupId;
    private String artefactId;
}
