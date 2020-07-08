package adventure.exceptions.objectsException;

/**
 * @author Corona-Extra
 */
public class ObjectsAmbiguityException extends ObjectsException {

    @Override
    public String getMessage() {
        return "There's an ambiguity with objects";
    }
}
