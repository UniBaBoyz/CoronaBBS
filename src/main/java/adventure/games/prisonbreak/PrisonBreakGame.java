package adventure.games.prisonbreak;

import adventure.games.GameDescription;
import adventure.games.prisonbreak.movement.ControllerMovement;
import adventure.parser.ParserOutput;
import adventure.type.Inventory;

import static adventure.games.prisonbreak.RoomType.MAIN_CELL;


/**
 * @author Corona-Extra
 */
public class PrisonBreakGame extends GameDescription {

    private ControllerMovement movement;

    public PrisonBreakGame() {
        super(new PrisonBreakObjects(), new PrisonBreakRooms(), new PrisonBreakVerbs());

        init();

        //Set starting room
        setCurrentRoom(getRoom(MAIN_CELL));

        //Set Inventory
        setInventory(new Inventory(5));
    }

    private void init() {
        getGameVerbs().initVerbs(this);
        getGameRooms().initRooms(this);
        getGameObjects().initObjects(this);
        this.movement = new ControllerMovement(this);
    }

    @Override
    public String nextMove(ParserOutput p) {
        return movement.nextMove(p);
    }
}
