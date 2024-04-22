package ru.itis.mocker.core.mappers;

import ru.itis.mocker.core.objects.*;
import ru.itis.mocker.core.utils.GenerateUtils;
import ru.itis.mocker.core.models.ClassModel;
import ru.itis.mocker.core.models.FieldModel;
import ru.itis.mocker.core.models.MockModel;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.StringUtils;
import ru.itis.mocker.core.utils.TypeUtils;

import java.util.Objects;
import java.util.Optional;

import static ru.itis.mocker.core.utils.StringUtils.*;
import static ru.itis.mocker.core.utils.StringUtils.addControllerName;
import static ru.itis.mocker.core.utils.TypeUtils.*;


public class ControllerMapper {

    private static final Integer LOWER_BOUND = 1;
    private static final Integer UPPER_BOUND = 60;
    public static ControllerObject mapToControllerObject(MockModel mockModel, MockerModel mockerModel) {
        return fillControllerObject(ControllerObject.builder()
                .groupId(mockerModel.getGroupId())
                .artefactId(mockerModel.getArtefactId())
                .name(addControllerName(capitalizeFirstLetter(mockModel.getName())))
                .requestObject(
                        RequestObject.builder()
                                .path(mockModel.getRequest().getPath())
                                .method(mockModel.getRequest().getMethod())
                                .queryParams(
                                        (mockModel.getRequest().getQueryParams() != null) ?
                                        mockModel.getRequest().getQueryParams().stream()
                                                .map(   query ->
                                                        QueryParamObject.builder()
                                                                .name(query.getName())
                                                                .isBoolean(isBoolean(query.getType()))
                                                                .isDouble(isDouble(query.getType()))
                                                                .isInteger(isInteger(query.getType()))
                                                                .isLong(isLong(query.getType()))
                                                                .isString(isString(query.getType()))
                                                                .required((query.getRequired() != null) ? query.getRequired() : false)
                                                                .isLast(false)
                                                                .build()
                                                ).toList()
                                                : null
                                )
                                .build()
                )
                .responseObject(
                        ResponseObject.builder()
                                .status(mockModel.getResponse().getStatus())
                                .AreExistedHeaders(!mockModel.getResponse().getHeaders().isEmpty() || mockModel.getResponse().getBody() != null)
                                .isExistedBody(isExistedBody(mockModel))
                                .body(generateBodyObject(mockModel, mockerModel))
                                .headers(mockModel.getResponse().getHeaders().stream().map(
                                        header -> HttpHeaderObject.builder()
                                                .name(header.getName())
                                                .value(header.getValue())
                                                .build()
                                ).toList())
                                .build()
                ).build());
    }

    private static Boolean isExistedBody(MockModel mockModel) {
        return mockModel.getResponse().getBodyRef() != null;
    }

    private static BodyObject generateBodyObject(MockModel mockModel, MockerModel mockerModel) {
        Optional<ClassModel> classModel = Optional.empty();
        if (mockModel.getResponse().getBodyRef() != null) {
           classModel = mockerModel.getClasses().stream()
                    .filter(model -> Objects.equals(mockModel.getResponse().getBodyRef(), model.getName()))
                    .findFirst();
        }
        if (classModel.isEmpty())
            return BodyObject.builder()
                    .bodyJson(mockModel.getResponse().getBody())
                    .build();

        return BodyObject.builder()
                .name(capitalizeFirstLetter(classModel.get().getName()))
                .fields(
                        classModel.get().getFields().stream()
                                .map(
                                        fieldModel -> FieldObject.builder()
                                                .isString(TypeUtils.isString(fieldModel.getType()))
                                                .isBoolean(isBoolean(fieldModel.getType()))
                                                .isLong(TypeUtils.isLong(fieldModel.getType()))
                                                .isInteger(TypeUtils.isInteger(fieldModel.getType()))
                                                .isDouble(TypeUtils.isDouble(fieldModel.getType()))
                                                .name(StringUtils.capitalizeFirstLetter(fieldModel.getName()))
                                                .primitiveValue(generateRandomValue(fieldModel))
                                                .build()
                                ).toList()
                )
                .bodyJson(mockModel.getResponse().getBody())
                .build();
    }

    private static String generateRandomValue(FieldModel fieldModel) {
        if (TypeUtils.isString(fieldModel.getType()))
            return GenerateUtils.generateRandomString(GenerateUtils.generateRandomInteger(LOWER_BOUND, UPPER_BOUND));
        if (isBoolean(fieldModel.getType()))
            return GenerateUtils.generateRandomBoolean();
        if (TypeUtils.isLong(fieldModel.getType()))
            return GenerateUtils.generateRandomLongToString(0, Long.MAX_VALUE);
        if (TypeUtils.isInteger(fieldModel.getType()))
            return GenerateUtils.generateRandomIntegerToString(0, Integer.MAX_VALUE);
        if (TypeUtils.isDouble(fieldModel.getType()))
            return GenerateUtils.generateRandomDoubleToString(0, Long.MAX_VALUE);

        throw new IllegalArgumentException("Such type not found");
    }

    private static ControllerObject fillControllerObject(ControllerObject controllerObject) {
        if (controllerObject.getRequestObject().getQueryParams() != null) {
            controllerObject.getRequestObject().getQueryParams()
                    .get(controllerObject.getRequestObject().getQueryParams().size() - 1)
                    .setIsLast(true);
        }
        return controllerObject;
    }


}
