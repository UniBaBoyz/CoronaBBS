package prisonbreak.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import prisonbreak.Exceptions.LexicalErrorException;

public class ScannerTokenIta extends ScannerToken {
    private final Token article = new Token(TokenType.ARTICLE);
    private final Token adjective = new Token(TokenType.ADJECTIVE);
    private final Token junction = new Token(TokenType.JUNCTION);

    public ScannerTokenIta() {
        super();
    }

    public ScannerTokenIta(String string) {
        super(string);
    }

    public void setArticles(Set<String> articles) {
        article.setAlias(articles);
    }

    public void setAdjective(Set<String> adjectives) {
        adjective.setAlias(adjectives);
    }

    public void setJunctions(Set<String> junctions) {
        junction.setAlias(junctions);
    }

    @Override
    public Iterator<Token> tokenize() throws Exception {
        List<Token> sentence = new ArrayList<>();
        String tokenizedString;

        tokenizedString = createTokenizedString();

        for (String stringToken : tokenizedString.split(String.valueOf(getSeparatorCharacter()))) {
            // TODO IMPROVEMENT REQUIRED
            Token token = null;
            if (getVerbs()
                    .stream()
                    .filter(t -> t.isAlias(stringToken))
                    .count() == 1) {
                token = getVerbs()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .findFirst()
                        .orElse(null);
            } else if (getObjects()
                    .stream()
                    .filter(t -> t.isAlias(stringToken))
                    .count() == 1) {
                token = getObjects()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .findFirst()
                        .orElse(null);
            } else if (article.isAlias(stringToken)) {
                token = article;
            } else if (adjective.isAlias(stringToken)) {
                token = adjective;
            } else if (junction.isAlias(stringToken)) {
                token = junction;
            } else {
                // If the stringToken is empty, the user has entered several consecutive skip characters
                if (!stringToken.isEmpty()) {
                    throw new LexicalErrorException();
                }
            }
            sentence.add(token);
        }

        return sentence.iterator();
    }

}
