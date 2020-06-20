package prisonbreak.parser;

import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;

import java.util.*;

public class ParserIta extends Parser {

    public ParserIta(Set<TokenVerb> verbs, Set<TokenObject> objects, Set<String> adjectives) {

        super(initValidPhrases(), verbs, objects, adjectives);
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

    private List<List<Token>> separatePhrases(Iterator<Token> iterator) {
        List<List<Token>> phrases = new ArrayList<>();

        List<Token> phrase = new ArrayList<>();

        while (iterator.hasNext()) {
            Token token = iterator.next();

            // If there is a junction then it means that there is another phrase
            if (token.getType().equals(TokenType.JUNCTION)) {

                // The phrase cannot end with a junction so i add the token to produce a SyntaxErrorException
                if (!iterator.hasNext()) {
                    phrase.add(token);
                }

                phrases.add(phrase);
                phrase = new ArrayList<>();
            } else {
                phrase.add(token);
            }
        }

        if (!phrase.isEmpty()) {
            phrases.add(phrase);
        }

        return phrases;
    }

    @Override
    public ScannerToken initScanner(Set<TokenVerb> verbs, Set<TokenObject> objects, Set<String> adjectives) {
        ScannerTokenIta scanner = new ScannerTokenIta();

        scanner.setSkipCharacters(new HashSet<>(Arrays.asList("\n", "\t", "\r", " ")));
        scanner.setVerbs(verbs);
        scanner.setObjects(objects);
        scanner.setArticles(new HashSet<>(Arrays.asList("il", "lo", "l'", "la", "i", "gli", "le", "un", "uno", "una", "un'", "del", "dello", "della", "dei", "degli", "delle")));
        scanner.setAdjective(adjectives);
        scanner.setJunctions(new HashSet<>(Arrays.asList("e", "dopodiche", "dopodiché", "dopodiche'", "dopodichè", "successivamente", "poi")));

        return scanner;
    }

    @Override
    public List<ParserOutput> parse(String stringToParse) throws Exception {
        List<List<Token>> phrases; //Each Token list is one sentence combined with another from TokenType.Junction
        Iterator<List<Token>> iterator;
        List<ParserOutput> parserOutputs = new ArrayList<>(); //For each sentence a ParserOutput is created
        List<TokenType> tokenPhrase;

        // Check the syntax of the phrase
        getScanner().setPhrase(stringToParse);
        phrases = separatePhrases(getScanner().tokenize());
        areValidPhrases(phrases);


        //TODO CREATE NEW METHOD
        /*iterator = phrases.iterator();
        while (iterator.hasNext()) {
            TokenVerb verb = null;
            TokenObject object = null;
            List<Token> phrase = iterator.next();

            for (Token i : phrase) {
                if (i.getType().equals(TokenType.VERB)) {
                    verb = (TokenVerb) i;
                } else if (i.getType().equals(TokenType.OBJECT)) {
                    object = (TokenObject) i;
                }
            }

            if (getScanner().getObjects().contains(object)) {
                parserOutputs.add(new ParserOutput(verb, object, null));
            }

        }*/
        // TODO END NEW METHOD

        return parserOutputs;
    }

}
