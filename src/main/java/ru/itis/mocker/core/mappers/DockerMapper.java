package ru.itis.mocker.core.mappers;

import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.objects.DockerObject;

public class DockerMapper {
    public static DockerObject mapToMavenObject(MockerModel mockerModel) {
        return DockerObject.builder()
                .artefactId(mockerModel.getArtefactId())
                .build();
    }
}
