package adventure.exceptions.objectsException;

public class ObjectNotFoundInRoomException extends ObjectsException {

    @Override
    public String getMessage() {
        return "The object has not been found in room!";
    }
}
