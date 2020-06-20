package prisonbreak.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;
import prisonbreak.utils.SyntaxErrorException;

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

            if (token.getType().equals(TokenType.JUNCTION)) {
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
    public List<ParserOutput> parse(String stringToParse, List<TokenObject> objects, List<TokenObject> inventory) throws Exception {
        List<List<Token>> phrases; //Ogni lista di Token è una frase unita ad un'altra da TokenType.Junction
        Iterator<List<Token>> iterator;
        List<ParserOutput> parserOutputs = new ArrayList<>(); //Ogni ParserOutput appartiene ad una frase
        List<TokenType> tokenPhrase;
        boolean validPhrase = true;

        getScanner().setPhrase(stringToParse);
        phrases = separatePhrases(getScanner().tokenize());

        iterator = phrases.iterator();
        while (validPhrase && iterator.hasNext()) {
            tokenPhrase = getTokenType(iterator.next());
            validPhrase = isValidPhrase(tokenPhrase);
        }

        if (!validPhrase) {
            throw new SyntaxErrorException();
        }

        iterator = phrases.iterator();
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

            if (objects.contains(object)) {
                parserOutputs.add(new ParserOutput(verb, object, null);
            }

        }

        return parserOutputs;
    }

    public List<TokenType> getTokenType(List<Token> phrase) {
        List<TokenType> tokens = new ArrayList<>();

        for (Token i : phrase) {
            tokens.add(i.getType());
        }
        return tokens;
    }

}
