package adventure.exceptions;

/**
 * @author Corona-Extra
 */
public class LockedRoomException extends Exception {

    @Override
    public String getMessage() {
        return "The room is locked";
    }
}
