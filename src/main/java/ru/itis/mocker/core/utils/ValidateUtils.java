package ru.itis.mocker.core.utils;

import ru.itis.mocker.core.consts.TypeConsts;
import ru.itis.mocker.core.models.FieldModel;
import ru.itis.mocker.core.models.MockModel;
import ru.itis.mocker.core.models.MockerModel;

import java.util.Objects;

public class ValidateUtils {
    public static Boolean validate(MockerModel mc) {
        if (mc.getArtefactId() == null || mc.getArtefactId().isEmpty()) {
            System.err.println("ArtefactId could not be empty or null!");
            return false;
        }

        if(mc.getGroupId() == null || mc.getGroupId().isEmpty()) {
            System.err.println("GroupId could not be empty or null!");
            return false;
        }

        if (mc.getClasses() != null && !mc.getClasses().isEmpty()) {

            if (
                    mc.getClasses()
                            .stream()
                            .anyMatch(classModel -> classModel.getName() == null || classModel.getName().isEmpty())
            ) {
                System.err.println("Classes names could not be empty or null!");
                return false;
            }

            if (
                    mc.getClasses().stream().flatMap(classModel -> classModel.getFields().stream())
                            .anyMatch(fieldModel -> fieldModel.getName() == null || fieldModel.getName().isEmpty())
            ) {
                System.err.println("Fields names could not be empty or null!");
                return false;
            }

            if (
                    mc.getClasses().stream().flatMap(classModel -> classModel.getFields().stream())
                            .map(FieldModel::getType)
                            .noneMatch(TypeUtils::isValidType)
            ) {
                System.err.println("Field types not supported!\n The list of types: " + TypeConsts.TYPES);
                return false;
            }
        }



        if (mc.getMocks() == null || mc.getMocks().isEmpty()) {
            System.err.println("Mocks could not be empty or null!");
            return false;
        }

        if (
                mc.getMocks().stream()
                        .anyMatch(mockModel -> mockModel.getName() == null || mockModel.getName().isEmpty())
        ) {
            System.err.println("Mocks names could not be empty or null!");
            return false;
        }

        if (
                mc.getMocks().stream()
                        .anyMatch(mockModel -> mockModel.getRequest() == null || mockModel.getResponse() == null)
        ) {
            System.err.println("Requests/Response could not be empty or null!");
            return false;
        }

        if (
                mc.getMocks().stream().map(mockModel -> mockModel.getRequest().getPath()).anyMatch(
                        path -> path == null || path.isEmpty()
                )
        ) {
            System.err.println("Paths could not be empty or null!");
            return false;
        }

        if (
                mc.getMocks().stream().map(mockModel -> mockModel.getRequest().getPath()).anyMatch(
                        path -> path == null || path.isEmpty()
                )
        ) {
            System.err.println("Paths could not be empty or null!");
            return false;
        }

        if (
                mc.getMocks().stream().map(mockModel -> mockModel.getRequest().getPath()).noneMatch(
                        URLUtils::isValidPath
                )
        ) {
            System.err.println("Paths url is not valid!");
            return false;
        }
        if (
                mc.getMocks().stream().map(MockModel::getRequest)
                        .filter(requestModel -> requestModel.getQueryParams() != null)
                        .flatMap(requestModel -> requestModel.getQueryParams().stream())
                        .anyMatch(queryParam -> queryParam.getName() == null || queryParam.getName().isEmpty())
        ) {
            System.err.println("QueryParams names could not be empty or null!");
            return false;
        }

        if (
                mc.getMocks().stream().map(MockModel::getRequest)
                        .filter(requestModel -> requestModel.getQueryParams() != null)
                        .flatMap(requestModel -> requestModel.getQueryParams().stream())
                        .anyMatch(queryParam -> queryParam.getType() == null || queryParam.getType().isEmpty())
        ) {
            System.err.println("QueryParams types could not be empty or null!\n The list of types: " + TypeConsts.TYPES );
            return false;
        }

        if (
                mc.getMocks().stream().map(MockModel::getRequest)
                        .filter(requestModel -> requestModel.getQueryParams() != null)
                        .flatMap(requestModel -> requestModel.getQueryParams().stream())
                        .noneMatch(queryParam ->TypeUtils.isValidType(queryParam.getType()))
        ) {
            System.err.println("QueryParams types is not valid!");
            return false;
        }

        if (
                mc.getMocks().stream().map(mockModel -> mockModel.getResponse().getStatus())
                        .anyMatch(Objects::isNull)
        ) {
            System.err.println("Responses status could not be null!");
            return false;
        }

        if (
                mc.getMocks().stream()
                        .map(MockModel::getResponse)
                        .filter(requestModel -> requestModel.getHeaders() != null)
                        .flatMap(requestModel -> requestModel.getHeaders().stream())
                        .anyMatch(httpHeaderModel -> httpHeaderModel.getName() == null || httpHeaderModel.getName().isEmpty())

        ) {
            System.err.println("Responses headers name could not be empty or null!");
            return false;
        }

        if (
            mc.getMocks().stream()
                    .map(MockModel::getResponse)
                    .filter(requestModel -> requestModel.getHeaders() != null)
                    .flatMap(requestModel -> requestModel.getHeaders().stream())
                .anyMatch(httpHeaderModel -> httpHeaderModel.getValue() == null)
        ) {
            System.err.println("Responses headers value could not be null!");
            return false;
        }

        return true;
    }
}
