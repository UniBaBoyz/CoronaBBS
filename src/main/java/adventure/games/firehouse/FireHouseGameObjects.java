package adventure.games.firehouse;

import adventure.games.GameDescription;
import adventure.games.ObjectsInterface;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author pierpaolo
 */
public class FireHouseGameObjects implements ObjectsInterface {

    @Override
    public void initObjects(GameDescription game) {
        TokenObject battery = new TokenObject(1, "Batteria", new HashSet<>(Arrays.asList("Batteria", "Pila")));
        game.getRoom(3).getObjects().add(battery);

        TokenObjectContainer wardrobe = new TokenObjectContainer(2, "Armadio", new HashSet<>(Arrays.asList("Guardaroba", "Vestiario")));
        wardrobe.setOpenable(true);
        wardrobe.setPickupable(false);
        wardrobe.setOpen(false);
        game.getRoom(4).getObjects().add(wardrobe);

        TokenObject toy = new TokenObject(3, "giocattolo", new HashSet<>(Arrays.asList("Gioco", "Robot")));
        toy.setPushable(true);
        toy.setPush(false);
        wardrobe.add(toy);
    }
}
