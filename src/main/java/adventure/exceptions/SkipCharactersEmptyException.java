package adventure.exceptions;

public class SkipCharactersEmptyException extends IllegalArgumentException {

    @Override
    public String getMessage() {
        return "There are no characters to skip";
    }
}
