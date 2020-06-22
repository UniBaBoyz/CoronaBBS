package prisonbreak.type;

import prisonbreak.parser.Token;
import prisonbreak.parser.TokenType;

import java.util.Set;

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
