package prisonbreak.utils;

public class LexicalErrorException extends InputErrorException {

    @Override
    public String getMessage() {
        return "There is an error with the lexical of the phrase";
    }
}