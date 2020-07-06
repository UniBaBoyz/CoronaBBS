package adventure.exceptions.objectsException;

/**
 * @author Corona-Extra
 */
public class ObjectNotFoundInRoomException extends ObjectsException {

    @Override
    public String getMessage() {
        return "The object has not been found in room!";
    }
}
