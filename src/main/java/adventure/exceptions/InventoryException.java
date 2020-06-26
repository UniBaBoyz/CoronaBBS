package adventure.exceptions;

/**
 * @author Corona-Extra
 */
public class InventoryException extends Exception {

    @Override
    public String getMessage() {
        return "There's a problem with your inventory!";
    }
}
