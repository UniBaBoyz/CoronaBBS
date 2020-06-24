package adventure.exceptions;

public class InventoryEmptyException extends InventoryException {

    @Override
    public String getMessage() {
        return "Your inventory is empty!";
    }
}
