package prisonbreak.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Parser {
    private ScannerToken scanner;
    private List<List<TokenType>> validPhrases;

    public Parser(List<List<TokenType>> validPhrases) {
        scanner = initScanner();
        this.validPhrases = new ArrayList<>(validPhrases);
    }

    public ScannerToken getScanner() {
        return scanner;
    }

    public List<List<TokenType>> getValidPhrases() {
        return validPhrases;
    }

    public boolean isValidPhrase(List<TokenType> phrase) {
        boolean validPhrase = false;

        Iterator<List<TokenType>> validPhraseIterator = validPhrases.iterator();
        while (validPhraseIterator.hasNext() && !validPhrase) {
            validPhrase = validPhraseIterator.next().equals(phrase);
        }

        return validPhrase;
    }

    public abstract ScannerToken initScanner();

    public abstract boolean parse(String stringToParse) throws Exception;

}
