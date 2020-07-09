package adventure.exceptions.objectException;

/**
 * @author Corona-Extra
 */
public class ObjectException extends Exception {

    @Override
    public String getMessage() {
        return "There's an error with the object";
    }
}
