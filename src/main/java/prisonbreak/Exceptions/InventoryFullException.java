package prisonbreak.Exceptions;

public class InventoryFullException extends InventoryException {

    @Override
    public String getMessage() {
        return "The inventory is full";
    }
}
