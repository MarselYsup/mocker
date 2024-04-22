package ru.itis.mocker.core.mappers;

import ru.itis.mocker.core.models.MockModel;
import ru.itis.mocker.core.objects.MavenObject;
import ru.itis.mocker.core.models.MockerModel;


public class MavenMapper {
    public static MavenObject mapToMavenObject(MockerModel mockerModel) {
        return MavenObject.builder()
                .artefactId(mockerModel.getArtefactId())
                .groupId(mockerModel.getGroupId())
                .queryParamEnabled(mockerModel.getMocks().stream().map(MockModel::getRequest).anyMatch(requestModel -> requestModel.getQueryParams() != null))
                .build();
    }
}
