package adventure.games.prisonbreak;

import adventure.games.GameDescription;
import adventure.games.prisonbreak.movement.ControllerMove;
import adventure.parser.ParserOutput;
import adventure.type.Inventory;

import static adventure.games.prisonbreak.RoomType.MAIN_CELL;


/**
 * @author Corona-Extra
 */
public class PrisonBreakGame extends GameDescription {

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
        ControllerMove.getInstance().setGame(this);
    }

    @Override
    public String nextMove(ParserOutput p) {
        return ControllerMove.getInstance().nextMove(p);
    }
}
