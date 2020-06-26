package adventure.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import adventure.exceptions.LexicalErrorException;

/**
 * @author Corona-Extra
 */
public class ScannerTokenIta extends ScannerToken {
    private final Token article = new Token(TokenType.ARTICLE);
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
                    .stream().anyMatch(t -> t.isAlias(stringToken))) {
                token = getVerbs()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .findFirst()
                        .orElse(null);
            } else if (getObjects()
                    .stream().anyMatch(t -> t.isAlias(stringToken))) {
                token = getObjects()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .findFirst()
                        .orElse(null);
            } else if (article.isAlias(stringToken)) {
                token = article;
            } else if (getAdjectives()
                    .stream()
                    .anyMatch(t -> t.isAlias(stringToken))) {
                token = getAdjectives()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .findFirst()
                        .orElse(null);
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
