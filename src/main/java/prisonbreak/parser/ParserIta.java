package prisonbreak.parser;

import java.util.*;

public class ParserIta extends Parser {
    private static List<List<TokenType>> validPhrases = new ArrayList<>();

    public ParserIta() {
        //initValidPhrases()
        super(validPhrases);
    }

    private void initValidPhrases() {
        // Accepted sentences form
        validPhrases.add(new ArrayList<>(Collections.singletonList(TokenType.VERB)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.OBJECT)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ARTICLE, TokenType.OBJECT)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.OBJECT, TokenType.ADJECTIVE)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ADJECTIVE, TokenType.OBJECT)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ARTICLE, TokenType.OBJECT, TokenType.ADJECTIVE)));
        validPhrases.add(new ArrayList<>(Arrays.asList(TokenType.VERB, TokenType.ARTICLE, TokenType.ADJECTIVE, TokenType.OBJECT)));
    }

    @Override
    public ScannerToken initScanner() {
        ScannerTokenIta scanner = new ScannerTokenIta();

        scanner.setSkipCharacters(new HashSet<>(Arrays.asList("\n", "\t", "", " ")));
        scanner.setVerbs(new HashSet<>(Arrays.asList("accendi", "prendi")));
        scanner.setObjects(new HashSet<>(Arrays.asList("computer", "mouse")));
        scanner.setArticles(new HashSet<>(Arrays.asList("il", "lo", "l'", "la", "i", "gli", "le", "un", "uno", "una", "un'", "del", "dello", "della", "dei", "degli", "delle")));
        scanner.setAdjective(new HashSet<>(Arrays.asList("rotto", "bianco")));
        scanner.setJunctions(new HashSet<>(Arrays.asList(",", "e", "dopo", "dopodiche", "dopodiché", "dopodiche'", "successivamente", "poi")));

        return scanner;
    }

    @Override
    public boolean parse() {
        // TODO COMPLETE
        return false;
    }

}
