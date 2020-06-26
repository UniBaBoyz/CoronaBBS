package adventure.exceptions;

/**
 * @author Corona-Extra
 */
public class SkipCharactersEmptyException extends IllegalArgumentException {

    @Override
    public String getMessage() {
        return "There are no characters to skip";
    }
}
