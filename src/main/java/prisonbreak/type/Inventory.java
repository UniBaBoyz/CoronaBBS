package prisonbreak.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import prisonbreak.Exceptions.InventoryFullException;
import prisonbreak.Exceptions.ObjectNotFoundException;

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

    public List<TokenObject> getInventory() {
        return list;
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
        if (list.size() > MAX_ITEM) {
            throw new InventoryFullException();
        }
        list.add(o);
    }

    public void remove(TokenObject o) throws ObjectNotFoundException {
        if (!list.contains(o)) {
            throw new ObjectNotFoundException();
        }

        list.remove(o);
    }
}
