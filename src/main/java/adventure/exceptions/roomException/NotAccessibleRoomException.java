package adventure.exceptions.roomException;

/**
 * @author Corona-Extra
 */
public class NotAccessibleRoomException extends RoomException {

    @Override
    public String getMessage() {
        return "The room is not accessible";
    }
}
