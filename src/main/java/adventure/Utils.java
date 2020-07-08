package adventure;

/**
 * @author Corona-Extra
 */
public class Utils {
    public final static String SEPARATOR_CHARACTER_STRING = ";";
    public final static String SEPARATOR_CHARACTER_DATE = "-";
    public final static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,40}$";
    public final static String REGISTRATION = "#REGISTRATION";
    public final static String CORRECT_REGISTRATION = "#OK_REGISTRATION";
    public final static String LOGIN = "#LOGIN";
    public final static String CORRECT_LOGIN = "#OK_LOGIN";
    public final static String NON_EXISTING_USER = "#NON_EXISTING_USER";
    public final static String INVALID_PASSWORD = "#INVALID_PASSWORD";
    public final static String EXISTING_USERNAME = "#EXISTING_USER";
    public final static String NEW_GAME = "#NEW_GAME";
    public final static String GAME_CREATED = "#GAME_CREATED";
    public final static String LOAD_GAME = "#LOAD_GAME";
    public final static String GAME_LOADED = "#GAME_LOADED";
    public final static String NO_GAME_FOUNDED = "#NO_GAME_FOUNDED";
    public final static String GAME_SAVED = "#GAME_SAVED";
    public final static String PRISON_BREAK = "#PRISON_BREAK";
    public final static String FIRE_HOUSE = "#FIRE_HOUSE";
    public final static String EXIT_GAME = "#EXIT_GAME";
    public final static String SAVE_GAME = "̄#SAVE_GAME";
    public final static String EXISTING_GAME = "#EXISTING_GAME";

    private static boolean isLeapYear(int year) {
        // A year is a leap year if it is divisible by four and not by one-hundred or if it is divisible by four-hundred
        return (((year % 4) == 0) && (year % 100) != 0) || (year % 400) == 0;
    }

    private static boolean isIncluded(int min, int max, int number) {
        return number >= min && number <= max;
    }

    public static boolean isValidDate(int day, int month, int year) {
        boolean valid_date = false;

        if (month == 2) {
            if (isLeapYear(year)) {
                // The year is a lap year so the month has twenty-nine days
                valid_date = isIncluded(1, 29, day);
            } else {
                // The year is not a lap year so the month has twenty-eight days
                valid_date = isIncluded(1, 28, day);
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            // The month has thirty days
            valid_date = isIncluded(1, 30, day);
        } else {
            // The month has thirty-one days
            valid_date = isIncluded(1, 31, day);
        }

        return valid_date;
    }
}
