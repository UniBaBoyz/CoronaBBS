package prisonbreak.parser;

import java.util.*;

public class ParserIta extends Parser {
    private List<List<TokenType>> phrases;

    public ParserIta() {
        super(initValidPhrases());
    }

    private static List<List<TokenType>> initValidPhrases() {
        List<List<TokenType>> validPhrases = new ArrayList<>();

        // Accepted sentences form
        validPhrases.add(new ArrayList<>(Collections.singletonList(TokenType.VERB)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.OBJECT)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ARTICLE, TokenType.OBJECT)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.OBJECT, TokenType.ADJECTIVE)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ADJECTIVE, TokenType.OBJECT)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ARTICLE, TokenType.OBJECT, TokenType.ADJECTIVE)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ARTICLE, TokenType.ADJECTIVE, TokenType.OBJECT)));

        return validPhrases;
    }

    private void separatePhrases(Iterable<TokenType> iterator) {
        //TODO COMPLETE
        phrases = new ArrayList<>();

        for (Iterator i = phrases.iterator(); i.hasNext(); i.next()) {

        }
    }

    @Override
    public ScannerToken initScanner() {
        ScannerTokenIta scanner = new ScannerTokenIta();

        scanner.setSkipCharacters(new HashSet<>(Arrays.asList("\n", "\t", "", " ")));
        scanner.setVerbs(new HashSet<>(Arrays.asList("accendi", "prendi")));
        scanner.setObjects(new HashSet<>(Arrays.asList("computer", "mouse")));
        scanner.setArticles(new HashSet<>(Arrays.asList("il", "lo", "l'", "la", "i", "gli", "le", "un", "uno", "una", "un'", "del", "dello", "della", "dei", "degli", "delle")));
        scanner.setAdjective(new HashSet<>(Arrays.asList("rotto", "bianco")));
        scanner.setJunctions(new HashSet<>(Arrays.asList(",", "e", "dopodiche", "dopodiché", "dopodiche'", "dopodichè", "successivamente", "poi")));

        return scanner;
    }

    @Override
    public boolean parse(String stringToParse) throws Exception {
        // TODO COMPLETE

        getScanner().setPhrase(stringToParse);

        // TODO EXCEPTION
        separatePhrases(getScanner().tokenize());


        return false;
    }

}
