package adventure.games.prisonbreak.movement;

import adventure.exceptions.LockedRoomException;
import adventure.exceptions.NotAccessibleRoomException;

class MovementVerbs {

    void nord() throws NotAccessibleRoomException, LockedRoomException {
        if (ControllerMove.getInstance().getGame().getCurrentRoom().getNorth() != null
                && !ControllerMove.getInstance().getGame().getCurrentRoom().getNorth().isLocked()) {
            ControllerMove.getInstance().getGame().setCurrentRoom(
                    ControllerMove.getInstance().getGame().getCurrentRoom().getNorth());
            ControllerMove.getInstance().setMove(true);
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getNorth() == null) {
            throw new NotAccessibleRoomException();
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getNorth().isLocked()) {
            throw new LockedRoomException();
        }
    }

    void south() throws NotAccessibleRoomException, LockedRoomException {
        if (ControllerMove.getInstance().getGame().getCurrentRoom().getSouth() != null
                && !ControllerMove.getInstance().getGame().getCurrentRoom().getSouth().isLocked()) {
            ControllerMove.getInstance().getGame().setCurrentRoom(
                    ControllerMove.getInstance().getGame().getCurrentRoom().getSouth());
            ControllerMove.getInstance().setMove(true);
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getSouth() == null) {
            throw new NotAccessibleRoomException();
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getSouth().isLocked()) {
            throw new LockedRoomException();
        }
    }

    void east() throws NotAccessibleRoomException, LockedRoomException {
        if (ControllerMove.getInstance().getGame().getCurrentRoom().getEast() != null
                && !ControllerMove.getInstance().getGame().getCurrentRoom().getEast().isLocked()) {
            ControllerMove.getInstance().getGame().setCurrentRoom(
                    ControllerMove.getInstance().getGame().getCurrentRoom().getEast());
            ControllerMove.getInstance().setMove(true);
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getEast() == null) {
            throw new NotAccessibleRoomException();
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getEast().isLocked()) {
            throw new LockedRoomException();
        }
    }

    void west() throws NotAccessibleRoomException, LockedRoomException {
        if (ControllerMove.getInstance().getGame().getCurrentRoom().getWest() != null
                && !ControllerMove.getInstance().getGame().getCurrentRoom().getWest().isLocked()) {
            ControllerMove.getInstance().getGame().setCurrentRoom(
                    ControllerMove.getInstance().getGame().getCurrentRoom().getWest());
            ControllerMove.getInstance().setMove(true);
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getWest() == null) {
            throw new NotAccessibleRoomException();
        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getWest().isLocked()) {
            throw new LockedRoomException();
        }
    }

}
