package ru.itis.mocker.core.models;

import lombok.*;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MockerModel {
    private String groupId;
    private String artefactId;
    private List<MockModel> mocks;
    private List<ClassModel> classes;
}
