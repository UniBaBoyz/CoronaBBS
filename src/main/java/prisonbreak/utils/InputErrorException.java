package prisonbreak.utils;

public class InputErrorException extends Exception {
    @Override
    public String getMessage() {
        // TODO FIX MESSAGE
        return "There was an error with the input";
    }
}
