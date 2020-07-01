package adventure.exceptions.objectsException;

public class ObjectsAmbiguityException extends ObjectsException {

    @Override
    public String getMessage() {
        return "There's an ambiguity with objects";
    }
}
