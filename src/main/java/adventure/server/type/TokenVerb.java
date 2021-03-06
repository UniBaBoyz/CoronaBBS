package adventure.server.type;

import adventure.server.parser.Token;
import adventure.server.parser.TokenType;

import java.util.Set;

/**
 * @author Corona-Extra
 */
public class TokenVerb extends Token {
    private final VerbType verbType;

    public TokenVerb(VerbType verbType) {
        super(TokenType.VERB);
        this.verbType = verbType;
    }

    public TokenVerb(VerbType verbType, Set<String> tokens) {
        super(TokenType.VERB, tokens);
        this.verbType = verbType;
    }

    public VerbType getVerbType() {
        return verbType;
    }
}
