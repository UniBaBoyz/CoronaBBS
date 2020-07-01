package adventure;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import adventure.exceptions.ObjectsAmbiguityException;
import adventure.parser.ParserOutput;
import adventure.type.Inventory;
import adventure.type.Room;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;
import adventure.type.TokenVerb;

/**
 * @author Corona-Extra
 */
public abstract class GameDescription {
    private static final int INCREASE_SCORE = 5;
    private final Set<Room> rooms = new HashSet<>();
    private final Set<TokenVerb> tokenVerbs = new HashSet<>();
    private final Set<TokenObject> objectNotAssignedRoom = new HashSet<>();
    private Inventory inventory;
    private Room currentRoom;
    private int score = 0;
    private boolean standUp = true; //TODO ELIMINARE SE SI CREA LA CLASSE PERSONAGGIO

    public static int getIncreaseScore() {
        return INCREASE_SCORE;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int id) {
        return rooms.stream().filter(room -> room.getId() == id).findFirst().orElse(null);
    }

    public Set<TokenVerb> getTokenVerbs() {
        return tokenVerbs;
    }

    public void setObjectNotAssignedRoom(Set<TokenObject> objectNotAssignedRoom) {
        this.objectNotAssignedRoom.addAll(objectNotAssignedRoom);
    }

    public void setObjectNotAssignedRoom(TokenObject objectNotAssignedRoom) {
        this.objectNotAssignedRoom.add(objectNotAssignedRoom);
    }

    public Set<TokenObject> getObjectNotAssignedRoom() {
        return objectNotAssignedRoom;
    }

    public void removeObjectNotAssigned(TokenObject object) {
        objectNotAssignedRoom.remove(object);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isStandUp() {
        return standUp;
    }

    public void setStandUp(boolean standUp) {
        this.standUp = standUp;
    }

    public abstract void init() throws Exception;

    public abstract void nextMove(ParserOutput p, PrintStream out);

    public Set<TokenObject> getObjects() {
        Set<TokenObject> objects = new HashSet<>(objectNotAssignedRoom);

        if (!rooms.isEmpty()) {
            for (Room i : rooms) {
                for (TokenObject o : i.getObjects()) {
                    objects.add(o);

                    if (o instanceof TokenObjectContainer) {
                        objects.addAll(((TokenObjectContainer) o).getObjects());
                    }
                }
            }
        }

        return objects;
    }

    public TokenObject getObject(int id) {
        return getObjects().stream().filter(tokenObject -> tokenObject.getId() == id).findFirst().orElse(null);
    }

    public Set<TokenAdjective> getAdjectives() {
        Set<TokenAdjective> adjectives = new HashSet<>();
        Set<TokenObject> objects = new HashSet<>(getObjects());

        if (!objects.isEmpty()) {
            for (TokenObject i : objects) {
                adjectives.addAll(i.getAdjectives());
            }
        }

        return adjectives;
    }

    public void increaseScore() {
        this.score += INCREASE_SCORE;
    }

    public int getScore() {
        return score;
    }

    public Set<TokenObjectContainer> getContainer() {
        Set<TokenObjectContainer> objectContainers = new HashSet<>();

        for (TokenObject o : getCurrentRoom().getObjects()) {
            if (o instanceof TokenObjectContainer) {
                objectContainers.add((TokenObjectContainer) o);
            }
        }

        return objectContainers;
    }

    public TokenObject getCorrectObject(Set<TokenObject> tokenObjects) throws ObjectsAmbiguityException {
        if (tokenObjects.stream()
                .filter(object -> getCurrentRoom().containsObject(object) || getInventory().contains(object))
                .count() > 1) {
            throw new ObjectsAmbiguityException();
        }

        return tokenObjects.stream()
                .filter(object -> getCurrentRoom().containsObject(object) || getInventory().contains(object))
                .findFirst().orElse(null);
    }
}
