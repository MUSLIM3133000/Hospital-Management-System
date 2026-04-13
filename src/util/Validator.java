package util;

/**
 * Utility class providing static input-validation helpers.
 * Centralises all validation logic so that every part of the system
 * applies the same rules — eliminating duplication and bugs.
 */
public final class Validator {

    // Prevent instantiation
    private Validator() {}

    /**
     * Returns true if the string is non-null and not blank (whitespace-only counts as blank).
     */
    public static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Returns true if ALL provided strings are non-empty.
     */
    public static boolean allNonEmpty(String... values) {
        for (String v : values) {
            if (!isNonEmpty(v)) return false;
        }
        return true;
    }

    /**
     * Parses a string to a positive integer.
     *
     * @param value String to parse
     * @param min   Minimum acceptable value (inclusive)
     * @param max   Maximum acceptable value (inclusive)
     * @return the parsed integer, or -1 if invalid
     */
    public static int parsePositiveInt(String value, int min, int max) {
        try {
            int n = Integer.parseInt(value.trim());
            return (n >= min && n <= max) ? n : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Returns true if the phone string contains only digits, spaces, +, -, ()
     * and is between 7 and 15 characters long.
     */
    public static boolean isValidPhone(String phone) {
        if (!isNonEmpty(phone)) return false;
        String cleaned = phone.replaceAll("[\\s+\\-()]", "");
        return cleaned.matches("\\d{7,15}");
    }

    /**
     * Returns true if the ID contains only alphanumeric characters and is 2–20 chars.
     */
    public static boolean isValidId(String id) {
        return isNonEmpty(id) && id.matches("[a-zA-Z0-9]{2,20}");
    }
}
