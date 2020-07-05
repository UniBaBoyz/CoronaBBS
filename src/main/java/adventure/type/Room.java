package adventure.type;

import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.games.prisonbreak.TokenPerson;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Corona-Extra
 */
public class Room {

    private final int id;
    private Set<TokenObject> objects = new HashSet<>();
    private Set<TokenObject> objectsUsableHere = new HashSet<>();
    private String name;
    private String description;
    private String look;
    private boolean visible = true;
    private boolean locked = false;
    private Room south = null;
    private Room north = null;
    private Room east = null;
    private Room west = null;

    public Room(int id) {
        this.id = id;
    }

    public Room(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setObject(TokenObject object) {
        objects.add(object);
    }

    public Set<TokenObject> getObjects() {
        Set<TokenObject> allObjects = new HashSet<>(objects);

        for (TokenObject obj : objects) {
            if (obj instanceof TokenObjectContainer && obj.isOpenable() && obj.isOpen()) {
                allObjects.addAll(((TokenObjectContainer) obj).getObjects());
            } else if (obj instanceof TokenPerson) {
                try {
                    allObjects.addAll(((TokenPerson) obj).getInventory().getObjects());
                } catch (InventoryEmptyException ignored) {

                }
            }
        }

        return objects = new HashSet<>(allObjects);
    }

    public void setObjects(Set<TokenObject> objects) {
        this.objects.addAll(objects);
    }

    public void removeObject(TokenObject object) {
        for (TokenObject obj : getObjects()) {
            if (obj instanceof TokenPerson) {
                if (((TokenPerson) obj).getInventory().contains(object)) {
                    try {
                        ((TokenPerson) obj).getInventory().remove(object);
                    } catch (ObjectNotFoundInInventoryException ignored) {

                    }
                }
            }
        }
        getObjects().remove(object);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setObjectsUsableHere(Set<TokenObject> objectsUsableHere) throws IllegalArgumentException {
        for (TokenObject object : objectsUsableHere) {
            setObjectsUsableHere(object);
        }
    }

    public void setObjectsUsableHere(TokenObject objectsUsableHere) throws IllegalArgumentException {
        if (!objectsUsableHere.isUsable()) {
            throw new IllegalArgumentException();
        }

        this.objectsUsableHere.add(objectsUsableHere);
    }

    public boolean isObjectUsableHere(TokenObject object) {
        return objectsUsableHere.contains(object);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLook() {
        Set<TokenObject> objects = getObjects();
        if (!objects.isEmpty()) {

            //Doesn't look the objects that have each person in the room
            for (TokenObject obj : getObjects()) {
                if (obj instanceof TokenPerson) {
                    try {
                        objects.removeAll(((TokenPerson) obj).getInventory().getObjects());
                    } catch (InventoryEmptyException ignored) {

                    }
                }
            }

            StringBuilder objectsDescription = new StringBuilder("Puoi notare: \n");

            for (TokenObject obj : objects) {
                objectsDescription.append(obj.getName())
                        .append(", ");
            }

            return look + "\n" + objectsDescription.substring(0, objectsDescription.length() - 2);
        }

        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public boolean containsObject(TokenObject object) {
        return getObjects().contains(object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
