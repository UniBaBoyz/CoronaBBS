package adventure.exceptions.inventoryException;

import adventure.exceptions.objectsException.ObjectsException;

/**
 * @author Corona-Extra
 */
public class ObjectNotFoundInInventoryException extends ObjectsException {

    @Override
    public String getMessage() {
        return "The object has not been found in inventory!";
    }
}
