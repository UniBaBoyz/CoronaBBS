package adventure.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import adventure.exceptions.InputErrorException;
import adventure.exceptions.SyntaxErrorException;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenVerb;

public abstract class Parser {
    private final ScannerToken scanner;
    private final List<List<TokenType>> validSentences;

    public Parser(List<List<TokenType>> validSentences, Set<TokenVerb> verbs, Set<TokenObject> objects, Set<TokenAdjective> adjectives) {
        scanner = initScanner(verbs, objects, adjectives);
        this.validSentences = new ArrayList<>(validSentences);
    }

    public ScannerToken getScanner() {
        return scanner;
    }

    public List<TokenType> getTokenType(List<Token> sentence) {
        List<TokenType> tokens = new ArrayList<>();

        for (Token i : sentence) {
            tokens.add(i.getType());
        }

        return tokens;
    }

    private boolean isValidSentence(List<TokenType> sentence) {
        boolean validSentence = false;

        Iterator<List<TokenType>> validSentenceIterator = validSentences.iterator();
        while (validSentenceIterator.hasNext() && !validSentence) {
            validSentence = validSentenceIterator.next().equals(sentence);
        }

        return validSentence;
    }

    public boolean areValidSentences(List<List<Token>> sentences) throws SyntaxErrorException {
        Iterator<List<Token>> iterator;
        List<TokenType> tokenSentence;
        boolean validSentence = true;

        iterator = sentences.iterator();
        while (validSentence && iterator.hasNext()) {
            tokenSentence = getTokenType(iterator.next());
            validSentence = isValidSentence(tokenSentence);
        }

        if (!validSentence) {
            throw new SyntaxErrorException();
        }

        return validSentence;
    }

    private TokenVerb getTokenVerb(List<Token> sentence) {
        TokenVerb token = null;

        for (Token i : sentence) {
            if (i.getType().equals(TokenType.VERB)) {
                token = (TokenVerb) i;
                break;
            }
        }

        return token;
    }

    private TokenObject getTokenObject(List<Token> sentence) {
        TokenObject token = null;

        for (Token i : sentence) {
            if (i.getType().equals(TokenType.OBJECT)) {
                token = (TokenObject) i;
                break;
            }
        }

        return token;
    }

    private TokenAdjective getTokenAdjective(List<Token> sentence) {
        TokenAdjective token = null;

        for (Token i : sentence) {
            if (i.getType().equals(TokenType.ADJECTIVE)) {
                token = (TokenAdjective) i;
                break;
            }
        }

        return token;
    }

    private boolean isCorrectAdjective(TokenObject object, TokenAdjective adjective) {
        boolean isAdjective = false;

        if (adjective != null && object != null) {
            for (String i : adjective.getAlias()) {
                if (object.getAdjectives().stream()
                        .anyMatch(t -> t.isAlias(i))) {
                    isAdjective = true;
                    break;
                }
            }
        }

        return isAdjective;
    }

    public List<ParserOutput> generateParserOutput(Iterator<List<Token>> sentences) throws InputErrorException {
        List<ParserOutput> parserOutputs = new ArrayList<>(); //For each sentence a ParserOutput is created
        List<Token> sentence;
        TokenVerb verb;
        TokenObject object;
        TokenAdjective adjective;

        while (sentences.hasNext()) {
            sentence = sentences.next();
            verb = getTokenVerb(sentence);
            object = getTokenObject(sentence);
            adjective = getTokenAdjective(sentence);

            // Check if the adjective refers to the exact object
            if (object != null && adjective != null && !isCorrectAdjective(object, adjective)) {
                throw new InputErrorException();
            }

            parserOutputs.add(new ParserOutput(verb, object, adjective));

        }

        return parserOutputs;
    }

    public abstract ScannerToken initScanner(Set<TokenVerb> verbs, Set<TokenObject> objects, Set<TokenAdjective> adjectives);

    public abstract List<ParserOutput> parse(String stringToParse) throws Exception;

}
