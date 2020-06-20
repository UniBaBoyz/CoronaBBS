package prisonbreak.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;

public abstract class Parser {
    private final ScannerToken scanner;
    private final List<List<TokenType>> validPhrases;

    public Parser(List<List<TokenType>> validPhrases, Set<TokenVerb> verbs, Set<TokenObject> objects, Set<String> adjectives) {
        scanner = initScanner(verbs, objects, adjectives);
        this.validPhrases = new ArrayList<>(validPhrases);
    }

    public ScannerToken getScanner() {
        return scanner;
    }

    public boolean isValidPhrase(List<TokenType> phrase) {
        boolean validPhrase = false;

        Iterator<List<TokenType>> validPhraseIterator = validPhrases.iterator();
        while (validPhraseIterator.hasNext() && !validPhrase) {
            validPhrase = validPhraseIterator.next().equals(phrase);
        }

        return validPhrase;
    }

    public abstract ScannerToken initScanner(Set<TokenVerb> verbs, Set<TokenObject> objects, Set<String> adjectives);

    public abstract List<ParserOutput> parse(String stringToParse, List<TokenObject> objects, List<TokenObject> inventory) throws Exception;

}
