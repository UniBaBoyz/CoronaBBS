package adventure.exceptions;

/**
 * @author Corona-Extra
 */
public class InventoryEmptyException extends InventoryException {

    @Override
    public String getMessage() {
        return "Your inventory is empty!";
    }
}
