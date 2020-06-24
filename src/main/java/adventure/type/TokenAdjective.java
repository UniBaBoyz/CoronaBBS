package adventure.type;

import java.util.Set;

import adventure.parser.Token;
import adventure.parser.TokenType;

public class TokenAdjective extends Token {

    public TokenAdjective() {
        super(TokenType.ADJECTIVE);
    }

    public TokenAdjective(Set<String> tokens) {
        super(TokenType.ADJECTIVE, tokens);
    }
}
