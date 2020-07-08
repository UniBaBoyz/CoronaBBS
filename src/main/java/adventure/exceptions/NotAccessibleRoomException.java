package adventure.exceptions;

/**
 * @author Corona-Extra
 */
public class NotAccessibleRoomException extends Exception {

    @Override
    public String getMessage() {
        return "The room is not accessible";
    }
}
