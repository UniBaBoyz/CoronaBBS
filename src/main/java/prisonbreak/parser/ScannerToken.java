package prisonbreak.parser;

import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;
import prisonbreak.Exceptions.SkipCharactersEmptyException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ScannerToken {
    private static final char SEPARATOR_CHARACTER = ';';
    private final Token skip = new Token(TokenType.SKIP);
    private Set<TokenVerb> verbs = new HashSet<>();
    private Set<TokenObject> objects = new HashSet<>();
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
        skip.setAlias(skipCharacters);
    }

    public void addVerb(TokenVerb verb) {
        verbs.add(verb);
    }

    public void setVerbs(Set<TokenVerb> verbs) {
        this.verbs = new HashSet<>(verbs);
    }

    public Set<TokenVerb> getVerbs() {
        return verbs;
    }

    public void addObject(TokenObject object) {
        objects.add(object);
    }

    public Set<TokenObject> getObjects() {
        return objects;
    }

    public void setObjects(Set<TokenObject> objects) {
        this.objects = new HashSet<>(objects);
    }

    public String createTokenizedString() throws SkipCharactersEmptyException {
        StringBuilder tokenizedString = new StringBuilder(stringToTokenize);

        if (skip.getAlias().isEmpty()) {
            throw new SkipCharactersEmptyException();
        }

        // Replacing all the character to skip with a separator character
        for (String token : skip.getAlias()) {
            tokenizedString = new StringBuilder(tokenizedString.toString().replace(token, String.valueOf(SEPARATOR_CHARACTER)));
        }

        return tokenizedString.toString();
    }

    public abstract Iterator<Token> tokenize() throws Exception;

    public void setPhrase(String string) {
        stringToTokenize = string;
    }

}
