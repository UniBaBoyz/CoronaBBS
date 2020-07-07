package adventure.games.prisonbreak;

import adventure.games.GameDescription;
import adventure.games.prisonbreak.movement.ControllerMovement;
import adventure.parser.ParserOutput;
import adventure.type.Inventory;

import java.io.Serializable;

import static adventure.games.prisonbreak.RoomType.MAIN_CELL;
import static adventure.utils.Utils.MAX_INVENTORY;


/**
 * @author Corona-Extra
 */
public class PrisonBreakGame extends GameDescription {

    private final ControllerMovement movement;

    public PrisonBreakGame() {
        super(new Objects(), new Rooms(), new Verbs(), "PrisonBreakGame");
        this.movement = new ControllerMovement(this);

        String introduction = "===========================================================================" +
                "======\n" + "\t\t BENVENUTO IN PRISON BREAK!!!\n" + "=====================================" +
                "============================================\n" + "Sei stato arrestato per aver commesso una" +
                " rapina nella banca centrale di New York! La corte giudiziaria ti ha dato 3 anni di carcere e " +
                "sotto tua richiesta sei stato collocato nel carcere di maggiore sicurezza di New York. Tutto" +
                " fa parte di un tuo malefico piano: salvare tuo fratello imprigionato all'interno dello stesso " +
                "carcere, accusato ingiustamente di aver commesso un omicidio. Il tempo non è dalla tua parte, " +
                "domani tuo fratello sarà giustiziato, riuscirai a evadere insieme a lui dal carcere senza farti " +
                "scoprire o uccidere???\n" + "====================================================================" +
                "=============\n";
        setIntroduction(introduction);

        //Set starting room
        setCurrentRoom(getRoom(MAIN_CELL));

        //Set Inventory
        setInventory(new Inventory(MAX_INVENTORY));
    }

    @Override
    public String nextMove(ParserOutput p) {
        return movement.nextMove(p);
    }
}
