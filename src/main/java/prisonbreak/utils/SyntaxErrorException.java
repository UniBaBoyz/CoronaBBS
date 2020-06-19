package prisonbreak.utils;

public class SyntaxErrorException extends InputErrorException {

    @Override
    public String getMessage() {
        return "There is an error with the syntax of the phrase";
    }
}
