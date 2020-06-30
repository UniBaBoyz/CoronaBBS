package adventure.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Iterator<Set<Token>> tokenize() throws Exception {
        List<Set<Token>> sentence = new ArrayList<>();
        String tokenizedString;

        tokenizedString = createTokenizedString();

        for (String stringToken : tokenizedString.split(String.valueOf(getSeparatorCharacter()))) {
            // TODO IMPROVEMENT REQUIRED
            Set<Token> token = new HashSet<>();
            if (getVerbs()
                    .stream().anyMatch(t -> t.isAlias(stringToken))) {
                token.add(getVerbs()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .findFirst().orElse(null));
            } else if (getObjects()
                    .stream().anyMatch(t -> t.isAlias(stringToken))) {
                token = getObjects()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .collect(Collectors.toSet());
            } else if (article.isAlias(stringToken)) {
                token.add(article);
            } else if (getAdjectives()
                    .stream()
                    .anyMatch(t -> t.isAlias(stringToken))) {
                token.add(getAdjectives()
                        .stream()
                        .filter(t -> t.isAlias(stringToken))
                        .findFirst().orElse(null));
            } else if (junction.isAlias(stringToken)) {
                token.add(junction);
            } else {
                // If the stringToken is empty, the user has entered several consecutive skip characters
                if (!stringToken.isEmpty()) {
                    throw new LexicalErrorException();
                }
            }

            if (!token.isEmpty()) {
                sentence.add(token);
            }
        }

        return sentence.iterator();
    }

}
