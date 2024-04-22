package ru.itis.mocker.core.objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MavenObject {
    private String groupId;
    private String artefactId;
    private Boolean queryParamEnabled;
}
