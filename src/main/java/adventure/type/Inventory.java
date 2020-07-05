package adventure.type;

import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Corona-Extra
 */
public class Inventory {
    private final int MAX_ITEM;
    private List<TokenObject> list = new ArrayList<>();

    public Inventory(int dimension) {
        this.MAX_ITEM = dimension;
    }

    public void setInventory(List<TokenObject> list) {
        this.list = list;
    }

    public List<TokenObject> getObjects() throws InventoryEmptyException {
        isEmpty();
        return list;
    }

    public boolean isEmpty() throws InventoryEmptyException {
        if (list.isEmpty()) {
            throw new InventoryEmptyException();
        }
        return false;
    }

    public boolean contains(TokenObject o) {
        if (o != null) {
            return list.contains(o);
        }
        return false;
    }

    public TokenObject getObject(int id) {
        Iterator<TokenObject> iterator = list.iterator();
        TokenObject o = null;

        while (iterator.hasNext()) {
            o = iterator.next();
            if (o.getId() == id) {
                return o;
            }
        }

        return o;
    }

    public void add(TokenObject o) throws InventoryFullException {
        if (o != null) {
            if (list.size() >= MAX_ITEM) {
                throw new InventoryFullException();
            }
            list.add(o);
        } else {
            throw new NullPointerException();
        }
    }

    public void remove(TokenObject o) throws ObjectNotFoundInInventoryException {
        if (o != null) {
            if (!list.contains(o)) {
                throw new ObjectNotFoundInInventoryException();
            }

            list.remove(o);
        } else {
            throw new NullPointerException();
        }
    }
}
