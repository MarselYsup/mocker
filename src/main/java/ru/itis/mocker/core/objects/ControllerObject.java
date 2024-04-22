package ru.itis.mocker.core.objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ControllerObject {
    private String groupId;
    private String artefactId;
    private String name;
    private RequestObject requestObject;
    private ResponseObject responseObject;
}
