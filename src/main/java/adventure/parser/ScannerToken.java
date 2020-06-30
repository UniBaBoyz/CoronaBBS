package adventure.parser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import adventure.exceptions.SkipCharactersEmptyException;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenVerb;

/**
 * @author Corona-Extra
 */
public abstract class ScannerToken {
    private static final char SEPARATOR_CHARACTER = ';';
    private final Token skip = new Token(TokenType.SKIP);
    private final Set<TokenVerb> verbs = new HashSet<>();
    private final Set<TokenObject> objects = new HashSet<>();
    private final Set<TokenAdjective> adjectives = new HashSet<>();
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

    public Set<TokenVerb> getVerbs() {
        return verbs;
    }

    public void setVerbs(Set<TokenVerb> verbs) {
        this.verbs.addAll(verbs);
    }

    public void addObject(TokenObject object) {
        objects.add(object);
    }

    public Set<TokenObject> getObjects() {
        return objects;
    }

    public void setObjects(Set<TokenObject> objects) {
        this.objects.addAll(objects);
    }

    public void addAdjective(TokenAdjective adjective) {
        adjectives.add(adjective);
    }

    public Set<TokenAdjective> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(Set<TokenAdjective> adjectives) {
        this.adjectives.addAll(adjectives);
    }

    public void setSentence(String string) {
        stringToTokenize = string;
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

    public abstract Iterator<Set<Token>> tokenize() throws Exception;

}
