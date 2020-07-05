package adventure.type;

import adventure.parser.Token;
import adventure.parser.TokenType;

import java.util.Set;

/**
 * @author Corona-Extra
 */
public class TokenAdjective extends Token {

    public TokenAdjective() {
        super(TokenType.ADJECTIVE);
    }

    public TokenAdjective(Set<String> tokens) {
        super(TokenType.ADJECTIVE, tokens);
    }
}
