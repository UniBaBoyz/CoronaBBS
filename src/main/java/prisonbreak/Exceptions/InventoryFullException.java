package prisonbreak.Exceptions;

public class InventoryFullException extends Exception {

    @Override
    public String getMessage() {
        return "The inventory is full";
    }
}
