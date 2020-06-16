package prisonbreak.parser;

import prisonbreak.utils.SkipCharactersEmptyException;

import java.util.ArrayList;
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
    public Iterable<TokenType> getPhrase() throws Exception {
        List<TokenType> phrase = new ArrayList<>();
        String tokenizedString;

        try {
            tokenizedString = createTokenizedString();
        } catch (SkipCharactersEmptyException e) {
            throw new IllegalAccessException();
        }

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
                // TODO CREATE NEW EXCEPTION
                throw new Exception("Lexical Exception");
            }
        }

        return phrase;
    }

}
