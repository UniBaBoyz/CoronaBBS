package adventure.exceptions;

public class LockedRoomException extends Exception {

    @Override
    public String getMessage() {
        return "The room is locked";
    }
}
