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
        article.addAliasToken(articles);
    }

    public void setAdjective(Set<String> adjectives) {
        adjective.addAliasToken(adjectives);
    }

    public void setJunctions(Set<String> junctions) {
        junction.addAliasToken(junctions);
    }

    @Override
    public Iterator<TokenType> tokenize() throws Exception {
        List<TokenType> phrase = new ArrayList<>();
        String tokenizedString;

        tokenizedString = createTokenizedString();

        for (String token : tokenizedString.split(String.valueOf(getSeparatorCharacter()))) {
            // TODO IMPROVEMENT REQUIRED
            if (getVerbs()
                    .stream()
                    .filter(t -> t.isToken(token))
                    .count() == 1) {
                phrase.add(TokenType.VERB);
            } else if (getObjectToken().isToken(token)) {
                phrase.add(TokenType.OBJECT);
            } else if (article.isToken(token)) {
                phrase.add(TokenType.ARTICLE);
            } else if (adjective.isToken(token)) {
                phrase.add(TokenType.ADJECTIVE);
            } else if (junction.isToken(token)) {
                phrase.add(TokenType.JUNCTION);
            } else {
                // If the token is empty, the user has entered several consecutive skip characters
                if (!token.isEmpty()) {
                    throw new LexicalErrorException();
                }
            }
        }

        return phrase.iterator();
    }

}
