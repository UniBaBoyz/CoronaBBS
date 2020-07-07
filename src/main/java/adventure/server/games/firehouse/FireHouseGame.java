package adventure.server.games.firehouse;

import adventure.exceptions.NotAccessibleRoomException;
import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.objectsException.ObjectsException;
import adventure.server.games.GameDescription;
import adventure.server.parser.ParserOutput;
import adventure.server.type.*;

import java.sql.SQLException;

/**
 * @author pierpaolo
 */
public class FireHouseGame extends GameDescription {

    public FireHouseGame() throws SQLException {
        super(new FireHouseGameObjects(), new FireHouseGameRooms(), new FireHouseVerbs(), "FireHouseGame");
        setCurrentRoom(getRoom(0));

        setInventory(new Inventory(10));
    }

    @Override
    public String nextMove(ParserOutput p) {
        StringBuilder response = new StringBuilder();

        try {
            if (p.getVerb() == null) {
                response.append("Non ho capito cosa devo fare! Prova con un altro comando.\n");
            } else {
                //move
                boolean move = false;
                TokenObject object = getCorrectObject(p.getObject());

                if(p.getVerb().getVerbType().equals(VerbType.NORD)) {
                    if (getCurrentRoom().getNorth() != null) {
                        setCurrentRoom(getCurrentRoom().getNorth());
                        move = true;
                    } else {
                        throw new NotAccessibleRoomException();
                    }

                } else if (p.getVerb().getVerbType().equals(VerbType.SOUTH)) {
                    if (getCurrentRoom().getSouth() != null) {
                        setCurrentRoom(getCurrentRoom().getSouth());
                        move = true;
                    } else {
                        throw new NotAccessibleRoomException();
                    }

                } else if (p.getVerb().getVerbType().equals(VerbType.EAST)) {
                    if (getCurrentRoom().getEast() != null) {
                        setCurrentRoom(getCurrentRoom().getEast());
                        move = true;
                    } else {
                        throw new NotAccessibleRoomException();
                    }

                } else if (p.getVerb().getVerbType().equals(VerbType.WEST)) {
                    if (getCurrentRoom().getWest() != null) {
                        setCurrentRoom(getCurrentRoom().getWest());
                        move = true;
                    } else {
                        throw new NotAccessibleRoomException();
                    }
                    
                } else if (p.getVerb().getVerbType().equals(VerbType.INVENTORY)) {
                    response.append("Nel tuo inventario ci sono:\n");
                    for (TokenObject o : getInventory().getObjects()) {
                        response.append(o.getName()).append(": ").append(o.getDescription()).append("\n");
                    }
                    
                } else if (p.getVerb().getVerbType().equals(VerbType.LOOK_AT)) {
                    if (getCurrentRoom().getLook() != null) {
                        response.append(getCurrentRoom().getLook());
                    } else {
                        response.append("Non c'è niente di interessante qui.\n");
                    }

                } else if (p.getVerb().getVerbType().equals(VerbType.PICK_UP)) {
                    if (object != null) {
                        if (object.isPickupable()) {
                            getInventory().add(object);
                            getCurrentRoom().removeObject(object);
                            response.append("Hai raccolto: ").append(object.getDescription()).append("\n");
                        } else {
                            response.append("Non puoi raccogliere questo oggetto.\n");
                        }
                    } else {
                        response.append("Non c'è niente da raccogliere qui.\n");
                    }
                } else if (p.getVerb().getVerbType().equals(VerbType.PUSH)) {
                    if (object != null && object.isPushable()) {
                        response.append("Hai premuto: \n").append(object.getName());
                        if (object.getId() == 3) {
                            response.append("Premi il pulsante del giocattolo e in seguito ad una forte esplosione la " +
                                    "tua casa prende fuoco...tu e tuoi famigliari cercate invano di salvarvi e venite " +
                                    "avvolti dalle fiamme...è stata una morte CALOROSA...addio!\n");
                        }
                    } else {
                        response.append("Non ci sono oggetti che puoi premere qui.\n");
                    }
                }
                if (move) {
                    response.append(getCurrentRoom().getName()).append("\n");
                    response.append("================================================\n");
                    response.append(getCurrentRoom().getDescription()).append("\n");
                }
            }
        } catch (NotAccessibleRoomException e) {
            response.append("Non si può entrare qui\n");
        } catch (InventoryFullException e) {
            response.append("Il tuo inventario e' pieno\n");
        } catch (InventoryEmptyException e) {
            response.append("Mi sono sbagliato, il tuo inventario e' vuoto\n");
        } catch (ObjectsException e) {
            response.append("Mi dispiace ma non capisco\n");
        } catch (Exception e) {
            response.append("E' successo qualcosa di spiacevole\n");
        }

        return response.toString();
    }
}