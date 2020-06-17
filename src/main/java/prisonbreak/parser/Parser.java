package prisonbreak.parser;

import java.util.ArrayList;
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

    public abstract ScannerToken initScanner();

    public abstract boolean parse(String stringToParse) throws Exception;

}
