package adventure.exceptions.objectException;

/**
 * @author Corona-Extra
 */
public class ObjectAmbiguityException extends ObjectException {

    @Override
    public String getMessage() {
        return "There's an ambiguity with objects";
    }
}
