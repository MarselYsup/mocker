package ru.itis.mocker.core.utils;

import ru.itis.mocker.core.consts.TypeConsts;
import ru.itis.mocker.core.models.FieldModel;

public final class TypeUtils {

    public static Boolean isBoolean(String field) {
        return field.equals(TypeConsts.BOOLEAN_TYPE);
    }

    public static Boolean isLong(String field) {
        return field.equals(TypeConsts.LONG_TYPE);
    }

    public static Boolean isInteger(String field) {
        return field.equals(TypeConsts.INTEGER_TYPE);
    }
    public static Boolean isString(String field) {
        return field.equals(TypeConsts.STRING_TYPE);
    }

    public static Boolean isDouble(String field) {return field.equals(TypeConsts.DOUBLE_TYPE);}

    public static Boolean isList(String field) {return field.equals(TypeConsts.LIST_TYPE);}

    public static Boolean isValidType(String field) {
        return TypeConsts.TYPES.stream().anyMatch(t -> t.equals(field));
    }
}
