package ru.itis.mocker.core.utils;

import static ru.itis.mocker.core.utils.StringUtils.*;

public class ClassUtils {
    public static String getClassNameFile(String name) {
        return String.format("%s.java", capitalizeFirstLetter(name));
    }
}
