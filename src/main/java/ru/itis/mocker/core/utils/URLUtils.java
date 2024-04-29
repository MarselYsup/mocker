package ru.itis.mocker.core.utils;

import java.util.regex.Pattern;

public class URLUtils {
    private static final Pattern URL_PATH_PATTERN = Pattern.compile("^/(?:[^\\s/\\\\]+/)*[^\\s/\\\\]*$");

    /**
     * Checks if the provided string is a valid URL path without the protocol and host.
     *
     * @param path The string to be checked.
     * @return true if the string is a valid URL path fragment, false otherwise.
     */
    public static boolean isValidPath(String path) {
        return URL_PATH_PATTERN.matcher(path).matches();
    }

}