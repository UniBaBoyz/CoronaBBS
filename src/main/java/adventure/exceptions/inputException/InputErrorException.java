package adventure.exceptions.inputException;

/**
 * @author Corona-Extra
 */
public class InputErrorException extends Exception {

    @Override
    public String getMessage() {
        return "There is an error with the input";
    }
}
