package ru.itis.mocker.core.utils;

import java.util.Random;

public class GenerateUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    /**
     * Generates a random String of the specified length.
     * @param length The length of the string to be generated.
     * @return A random String of characters of the specified length as a String.
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    /**
     * Generates a random boolean value.
     * @return A randomly generated boolean value (true or false) as a String.
     */
    public static String generateRandomBoolean() {
        return String.valueOf(random.nextBoolean());
    }

    /**
     * Generates a random Integer within the specified range.
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (exclusive).
     * @return A randomly generated Integer within the specified range as a String.
     */
    public static String generateRandomIntegerToString(int lowerBound, int upperBound) {
        return String.valueOf(generateRandomInteger(lowerBound, upperBound));
    }


    /**
     * Generates a random Integer within the specified range.
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (exclusive).
     * @return A randomly generated Integer
     */
    public static Integer generateRandomInteger(int lowerBound, int upperBound) {
        return lowerBound + random.nextInt(upperBound - lowerBound);
    }

    /**
     * Generates a random Long value within the specified range.
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (exclusive).
     * @return A randomly generated Long value within the specified range as a String.
     */
    public static String generateRandomLongToString(long lowerBound, long upperBound) {
        return String.valueOf(generateRandomLong(lowerBound, upperBound) + "L");
    }

    /**
     * Generates a random Long value within the specified range.
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (exclusive).
     * @return A randomly generated Long value
     */
    public static Long generateRandomLong(long lowerBound, long upperBound) {
        return lowerBound + (long) (random.nextDouble() * (upperBound - lowerBound));
    }


    /**
     * Generates a random Long value within the specified range.
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (exclusive).
     * @return A randomly generated Double value
     */
    public static Double generateRandomDouble(long lowerBound, long upperBound) {
        return lowerBound +  (random.nextDouble() * (upperBound - lowerBound));
    }

    /**
     * Generates a random Long value within the specified range.
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (exclusive).
     * @return A randomly generated Double value within the specified range as a String.
     */
    public static String generateRandomDoubleToString(long lowerBound, long upperBound) {
        return String.valueOf(generateRandomDouble(lowerBound, upperBound));
    }
}
