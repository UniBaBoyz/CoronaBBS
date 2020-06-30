package adventure.exceptions;

public class ObjectsAmbiguityException extends Exception {

    @Override
    public String getMessage() {
        return "There's an ambiguity with objects";
    }
}
