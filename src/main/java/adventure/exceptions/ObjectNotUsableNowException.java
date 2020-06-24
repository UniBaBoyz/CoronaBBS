package adventure.exceptions;

public class ObjectNotUsableNowException extends Exception {

    @Override
    public String getMessage() {
        return "The object is not usable now";
    }
}
