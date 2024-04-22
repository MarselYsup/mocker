package ru.itis.mocker.core.mappers;

import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.objects.ApplicationObject;

public class ApplicationMapper {
    public static ApplicationObject mapToApplicationObject(MockerModel mockerModel) {
        return ApplicationObject.builder()
                .artefactId(mockerModel.getArtefactId())
                .groupId(mockerModel.getGroupId())
                .build();
    }
}
