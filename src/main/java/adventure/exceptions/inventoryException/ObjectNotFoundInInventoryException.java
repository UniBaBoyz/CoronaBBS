package adventure.exceptions.inventoryException;

import adventure.exceptions.objectException.ObjectException;

/**
 * @author Corona-Extra
 */
public class ObjectNotFoundInInventoryException extends ObjectException {

    @Override
    public String getMessage() {
        return "The object has not been found in inventory!";
    }
}
