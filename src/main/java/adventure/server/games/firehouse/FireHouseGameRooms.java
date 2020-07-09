package adventure.server.games.firehouse;

import adventure.server.games.GameDescription;
import adventure.server.games.RoomsInterface;
import adventure.server.type.Room;

import java.io.Serializable;

/**
 * @author pierpaolo con restauro da parte del team Corona-Extra
 */
public class FireHouseGameRooms implements RoomsInterface, Serializable {

    @Override
    public void initRooms(GameDescription game) {
        Room hall = new Room(0, "Corridoio", "Sei appena tornato a casa e non sai cosa fare. Ti ricordi che non hai ancora aperto quel fantastico regalo di tua zia Lina."
                + " Sarà il caso di cercarlo e di giocarci!");
        hall.setLook("Sei nel corridoio, a nord vedi il bagno, a sud il soggiorno e ad ovest la tua cameretta, forse il gioco sarà lì?");

        Room livingRoom = new Room(1, "Soggiorno", "Ti trovi nel soggiorno. Ci sono quei mobili marrone scuro che hai sempre odiato e delle orribili sedie.");
        livingRoom.setLook("Non c'è nulla di interessante qui.");

        Room kitchen = new Room(2, "Cucina", "Ti trovi nella solita cucina. Mobili bianchi, maniglie azzurre, quello strano lampadario che adoravi tanto quando eri piccolo. "
                + "C'è un tavolo con un bel portafrutta e una finestra.");
        kitchen.setLook("La solita cucina, ma noti una chiave vicino al portafrutta.");

        Room bathroom = new Room(3, "Bagno", "Sei nel bagno. Quanto tempo passato qui dentro...meglio non pensarci...");
        bathroom.setLook("Vedo delle batterie sul mobile alla destra del lavandino.");

        Room yourRoom = new Room(4, "La tua cameratta", "Finalmente la tua cameretta! Questo luogo ti è così famigliare...ma non ricordi dove hai messo il nuovo regalo di zia Lina.");
        yourRoom.setLook("C'è un armadio bianco, di solito conservi lì i tuoi giochi.");

        //maps
        kitchen.setEast(livingRoom);
        livingRoom.setNorth(hall);
        livingRoom.setWest(kitchen);
        hall.setSouth(livingRoom);
        hall.setWest(yourRoom);
        hall.setNorth(bathroom);
        bathroom.setSouth(hall);
        yourRoom.setEast(hall);
        game.getRooms().add(kitchen);
        game.getRooms().add(livingRoom);
        game.getRooms().add(hall);
        game.getRooms().add(bathroom);
        game.getRooms().add(yourRoom);
    }
}
