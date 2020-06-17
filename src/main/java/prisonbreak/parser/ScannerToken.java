package prisonbreak.parser;

import prisonbreak.utils.SkipCharactersEmptyException;

import java.util.Set;

public abstract class ScannerToken {
    private static final char SEPARATOR_CHARACTER = '|';
    private final Token skip = new Token(TokenType.SKIP);
    private final Token verb = new Token(TokenType.VERB);
    private final Token object = new Token(TokenType.OBJECT);
    private String stringToTokenize;

    public ScannerToken() {
    }

    public ScannerToken(String string) {
        stringToTokenize = string;
    }

    public static char getSeparatorCharacter() {
        return SEPARATOR_CHARACTER;
    }

    public void setSkipCharacters(Set<String> skipCharacters) {
        skip.addAliasToken(skipCharacters);
    }

    public void setVerbs(Set<String> verbs) {
        verb.addAliasToken(verbs);
    }

    public Token getVerbToken() {
        return verb;
    }

    public void setObjects(Set<String> objects) {
        object.addAliasToken(objects);
    }

    public Token getObjectToken() {
        return object;
    }

    public String createTokenizedString() throws SkipCharactersEmptyException {
        StringBuilder tokenizedString = new StringBuilder(stringToTokenize);

        if (skip.getTokens().isEmpty()) {
            throw new SkipCharactersEmptyException();
        }

        // Replacing all the character to skip with a separator character
        for (String token : skip.getTokens()) {
            tokenizedString = new StringBuilder(tokenizedString.toString().replace(token, String.valueOf(SEPARATOR_CHARACTER)));
        }

        return tokenizedString.toString();
    }

    public abstract Iterable<TokenType> tokenize() throws Exception;

    public void setPhrase(String string) {
        stringToTokenize = string;
    }

}
