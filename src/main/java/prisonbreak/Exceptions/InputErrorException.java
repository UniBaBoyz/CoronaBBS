package prisonbreak.Exceptions;

public class InputErrorException extends Exception {

    @Override
    public String getMessage() {
        return "There is an error with the input";
    }
}
