package prisonbreak.parser;

import prisonbreak.Exceptions.InputErrorException;
import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;
import prisonbreak.Exceptions.SyntaxErrorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class Parser {
    private final ScannerToken scanner;
    private final List<List<TokenType>> validsentences;

    public Parser(List<List<TokenType>> validsentences, Set<TokenVerb> verbs, Set<TokenObject> objects, Set<String> adjectives) {
        scanner = initScanner(verbs, objects, adjectives);
        this.validsentences = new ArrayList<>(validsentences);
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

    private boolean isValidsentence(List<TokenType> sentence) {
        boolean validsentence = false;

        Iterator<List<TokenType>> validsentenceIterator = validsentences.iterator();
        while (validsentenceIterator.hasNext() && !validsentence) {
            validsentence = validsentenceIterator.next().equals(sentence);
        }

        return validsentence;
    }

    public boolean areValidsentences(List<List<Token>> sentences) throws Exception {
        Iterator<List<Token>> iterator;
        List<TokenType> tokensentence;
        boolean validsentence = true;

        iterator = sentences.iterator();
        while (validsentence && iterator.hasNext()) {
            tokensentence = getTokenType(iterator.next());
            validsentence = isValidsentence(tokensentence);
        }

        if (!validsentence) {
            throw new SyntaxErrorException();
        }

        return validsentence;
    }

    public List<ParserOutput> generateParserOutput(Iterator<List<Token>> sentences) throws InputErrorException {
        List<ParserOutput> parserOutputs = new ArrayList<>(); //For each sentence a ParserOutput is created

        while (sentences.hasNext()) {
            TokenVerb verb = null;
            TokenObject object = null;
            Token adjective = null;
            List<Token> sentence = sentences.next();
            boolean isAdjective = false;

            for (Token i : sentence) {
                if (i.getType().equals(TokenType.VERB)) {
                    verb = (TokenVerb) i;
                } else if (i.getType().equals(TokenType.OBJECT)) {
                    object = (TokenObject) i;
                }
                if (i.getType().equals(TokenType.ADJECTIVE)) {
                    adjective = i;
                }
            }

            if (adjective != null && object != null) {
                for (String i : adjective.getAlias()) {
                    if (object.isAlias(i)) {
                        isAdjective = true;
                    }
                }
            }

            if (!isAdjective) {
                throw new InputErrorException();
            }

            parserOutputs.add(new ParserOutput(verb, object));

        }

        return parserOutputs;
    }

    public abstract ScannerToken initScanner(Set<TokenVerb> verbs, Set<TokenObject> objects, Set<String> adjectives);

    public abstract List<ParserOutput> parse(String stringToParse) throws Exception;

}
