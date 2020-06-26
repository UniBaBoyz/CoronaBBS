package adventure.exceptions;

/**
 * @author Corona-Extra
 */
public class ObjectNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "The object has not been found";
    }
}
