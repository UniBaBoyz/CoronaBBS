package prisonbreak.Exceptions;

public class InventoryException extends Exception {

    @Override
    public String getMessage() {
        return "There's a problem with your inventory!";
    }
}
