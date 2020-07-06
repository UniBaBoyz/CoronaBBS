package adventure.exceptions.objectsException;

/**
 * @author Corona-Extra
 */
public class ObjectsException extends Exception {

    @Override
    public String getMessage() {
        return "There's an error with the object";
    }
}
