package ru.itis.mocker.core.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String splitByDotAndJoinWithSlash(String input) {
        if (input == null) {
            return null;
        }

        // Split the string by "." and rejoin using "/"
        String[] parts = input.split("\\.");
        return String.join("/", parts);
    }

    public static String addControllerName(String name) {
        return String.format("%s%s", name, "Controller");
    }

    public static String formatString(String str) {
        return str.replace("&quot;", "\\\"")
                .replace("&#10;", "\\n")
                .replace("&#9;", "\\t");
    }

    public static void writeToAFile(String fileOutputPath, String content) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(fileOutputPath), StandardCharsets.UTF_8)) {
            outputStreamWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
