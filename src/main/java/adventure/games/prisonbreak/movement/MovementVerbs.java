package adventure.games.prisonbreak.movement;

import adventure.exceptions.LockedRoomException;
import adventure.exceptions.NotAccessibleRoomException;
import adventure.games.prisonbreak.PrisonBreakGame;

/**
 * @author Corona-Extra
 */
class MovementVerbs {

    private final Move movement;
    private final PrisonBreakGame prisonBreakGame;

    MovementVerbs(ControllerMovement controller) {
        movement = controller.getMove();
        prisonBreakGame = controller.getMove().getPrisonBreakGame();
    }

    void nord() throws NotAccessibleRoomException, LockedRoomException {
        if (prisonBreakGame.getCurrentRoom().getNorth() != null && !prisonBreakGame.getCurrentRoom().getNorth().isLocked()) {
            prisonBreakGame.setCurrentRoom(prisonBreakGame.getCurrentRoom().getNorth());
            movement.setMove(true);
        } else if (prisonBreakGame.getCurrentRoom().getNorth() == null) {
            throw new NotAccessibleRoomException();
        } else if (prisonBreakGame.getCurrentRoom().getNorth().isLocked()) {
            throw new LockedRoomException();
        }
    }

    void south() throws NotAccessibleRoomException, LockedRoomException {
        if (prisonBreakGame.getCurrentRoom().getSouth() != null && !prisonBreakGame.getCurrentRoom().getSouth().isLocked()) {
            prisonBreakGame.setCurrentRoom(prisonBreakGame.getCurrentRoom().getSouth());
            movement.setMove(true);
        } else if (prisonBreakGame.getCurrentRoom().getSouth() == null) {
            throw new NotAccessibleRoomException();
        } else if (prisonBreakGame.getCurrentRoom().getSouth().isLocked()) {
            throw new LockedRoomException();
        }
    }

    void east() throws NotAccessibleRoomException, LockedRoomException {
        if (prisonBreakGame.getCurrentRoom().getEast() != null && !prisonBreakGame.getCurrentRoom().getEast().isLocked()) {
            prisonBreakGame.setCurrentRoom(prisonBreakGame.getCurrentRoom().getEast());
            movement.setMove(true);
        } else if (prisonBreakGame.getCurrentRoom().getEast() == null) {
            throw new NotAccessibleRoomException();
        } else if (prisonBreakGame.getCurrentRoom().getEast().isLocked()) {
            throw new LockedRoomException();
        }
    }

    void west() throws NotAccessibleRoomException, LockedRoomException {
        if (prisonBreakGame.getCurrentRoom().getWest() != null && !prisonBreakGame.getCurrentRoom().getWest().isLocked()) {
            prisonBreakGame.setCurrentRoom(prisonBreakGame.getCurrentRoom().getWest());
            movement.setMove(true);
        } else if (prisonBreakGame.getCurrentRoom().getWest() == null) {
            throw new NotAccessibleRoomException();
        } else if (prisonBreakGame.getCurrentRoom().getWest().isLocked()) {
            throw new LockedRoomException();
        }
    }

}
