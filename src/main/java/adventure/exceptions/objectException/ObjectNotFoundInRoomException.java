package adventure.exceptions.objectException;

/**
 * @author Corona-Extra
 */
public class ObjectNotFoundInRoomException extends ObjectException {

    @Override
    public String getMessage() {
        return "The object has not been found in room!";
    }
}
