package prisonbreak.parser;

import prisonbreak.utils.LexicalErrorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
        article.addAlias(articles);
    }

    public void setAdjective(Set<String> adjectives) {
        adjective.addAlias(adjectives);
    }

    public void setJunctions(Set<String> junctions) {
        junction.addAlias(junctions);
    }

    @Override
    public Iterator<Token> tokenize() throws Exception {
        List<Token> phrase = new ArrayList<>();
        String tokenizedString;

        tokenizedString = createTokenizedString();

        for (String stringToken : tokenizedString.split(String.valueOf(getSeparatorCharacter()))) {
            // TODO IMPROVEMENT REQUIRED
            Token token;
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
                phrase.add(TokenType.JUNCTION);
            } else {
                // If the stringToken is empty, the user has entered several consecutive skip characters
                if (!stringToken.isEmpty()) {
                    throw new LexicalErrorException();
                }
            }
        }

        return phrase.iterator();
    }

}
