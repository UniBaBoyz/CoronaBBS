package adventure.exceptions.objectsException;

public class ObjectsException extends Exception {

    @Override
    public String getMessage() {
        return "There's an error with the object";
    }
}
