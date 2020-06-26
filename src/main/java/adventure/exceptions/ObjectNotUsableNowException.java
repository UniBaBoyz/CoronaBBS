package adventure.exceptions;

/**
 * @author Corona-Extra
 */
public class ObjectNotUsableNowException extends Exception {

    @Override
    public String getMessage() {
        return "The object is not usable now";
    }
}
