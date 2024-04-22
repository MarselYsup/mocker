package ru.itis.mocker.core.mappers;

import ru.itis.mocker.core.models.ClassModel;
import ru.itis.mocker.core.models.FieldModel;
import ru.itis.mocker.core.models.FieldTypeModel;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.objects.ClassObject;
import ru.itis.mocker.core.objects.FieldClassObject;
import ru.itis.mocker.core.objects.FieldTypeClassObject;
import ru.itis.mocker.core.utils.StringUtils;

import static ru.itis.mocker.core.utils.TypeUtils.*;

public class ClassMapper {

    public static ClassObject mapToClassObject(ClassModel classModel, MockerModel mockerModel) {
        return ClassObject.builder()
                .name(StringUtils.capitalizeFirstLetter(classModel.getName()))
                .artefactId(mockerModel.getArtefactId())
                .groupId(mockerModel.getGroupId())
                .fields(
                        classModel.getFields().stream().map(fieldModel -> FieldClassObject.builder()
                                .name(fieldModel.getName())
                                .doc(fieldModel.getDoc())
                                .isString(isString(fieldModel.getType()))
                                .isInteger(isInteger(fieldModel.getType()))
                                .isBoolean(isBoolean(fieldModel.getType()))
                                .isLong(isLong(fieldModel.getType()))
                                .isDouble(isDouble(fieldModel.getType()))
                                .isList(isList(fieldModel.getType()))
                                .item(
                                        (isList(fieldModel.getType())) ? mapToFieldTypeClassObject(fieldModel.getItem()) : null
                                )
                                .build()).toList()
                )
                .build();
    }

    private static FieldTypeClassObject mapToFieldTypeClassObject(FieldTypeModel fieldTypeModel) {
        return FieldTypeClassObject.builder()
                .isString(isString(fieldTypeModel.getType()))
                .isInteger(isInteger(fieldTypeModel.getType()))
                .isBoolean(isBoolean(fieldTypeModel.getType()))
                .isLong(isLong(fieldTypeModel.getType()))
                .isDouble(isDouble(fieldTypeModel.getType()))
                .isList(isList(fieldTypeModel.getType()))
                .item(
                        (isList(fieldTypeModel.getType())) ? mapToFieldTypeClassObject(fieldTypeModel.getItem()) : null
                )
                .build();
    }

}
