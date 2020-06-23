package prisonbreak.type;

import java.util.Set;

import prisonbreak.parser.Token;
import prisonbreak.parser.TokenType;

public class TokenAdjective extends Token {

    public TokenAdjective() {
        super(TokenType.ADJECTIVE);
    }

    public TokenAdjective(Set<String> tokens) {
        super(TokenType.ADJECTIVE, tokens);
    }
}
