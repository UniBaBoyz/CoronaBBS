package adventure.exceptions.roomException;

/**
 * @author Corona-Extra
 */
public class LockedRoomException extends RoomException {

    @Override
    public String getMessage() {
        return "The room is locked";
    }
}
