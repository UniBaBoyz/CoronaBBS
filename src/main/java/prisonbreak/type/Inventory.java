package prisonbreak.type;

import java.util.ArrayList;
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

    public List<TokenObject> getList() {
        return list;
    }

    public void setList(List<TokenObject> list) {
        this.list = list;
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
