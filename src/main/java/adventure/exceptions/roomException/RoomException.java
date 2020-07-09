package adventure.exceptions.roomException;

public class RoomException extends Exception {

    @Override
    public String getMessage() {
        return "There's an error with the room";
    }
}
