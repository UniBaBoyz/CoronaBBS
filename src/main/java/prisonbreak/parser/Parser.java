package prisonbreak.parser;

import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;
import prisonbreak.utils.SyntaxErrorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    public List<TokenType> getTokenType(List<Token> phrase) {
        List<TokenType> tokens = new ArrayList<>();

        for (Token i : phrase) {
            tokens.add(i.getType());
        }
        return tokens;
    }

    private boolean isValidPhrase(List<TokenType> phrase) {
        boolean validPhrase = false;

        Iterator<List<TokenType>> validPhraseIterator = validPhrases.iterator();
        while (validPhraseIterator.hasNext() && !validPhrase) {
            validPhrase = validPhraseIterator.next().equals(phrase);
        }

        return validPhrase;
    }

    public boolean areValidPhrases(List<List<Token>> phrases) throws Exception {
        Iterator<List<Token>> iterator;
        List<TokenType> tokenPhrase;
        boolean validPhrase = true;

        iterator = phrases.iterator();
        while (validPhrase && iterator.hasNext()) {
            tokenPhrase = getTokenType(iterator.next());
            validPhrase = isValidPhrase(tokenPhrase);
        }

        if (!validPhrase) {
            throw new SyntaxErrorException();
        }

        return validPhrase;
    }

    public abstract ScannerToken initScanner(Set<TokenVerb> verbs, Set<TokenObject> objects, Set<String> adjectives);

    public abstract List<ParserOutput> parse(String stringToParse) throws Exception;

}
