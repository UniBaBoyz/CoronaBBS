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

    public Token getJunction() {
        return junction;
    }

    @Override
    public Iterator<TokenType> tokenize() throws Exception {
        List<TokenType> phrase = new ArrayList<>();
        String tokenizedString;

        tokenizedString = createTokenizedString();

        for (String token : tokenizedString.split(String.valueOf(getSeparatorCharacter()))) {
            //TODO
            //CHECK IF A token IS A TOKEN

            // TODO IMPROVEMENT REQUIRED
            if (getVerbToken().isToken(token)) {
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
                throw new LexicalErrorException();
            }
        }

        return phrase.iterator();
    }

}
