package adventure.type;

import adventure.parser.Token;
import adventure.parser.TokenType;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenAdjective that = (TokenAdjective) o;
        return super.getAlias().equals(that.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getAlias());
    }
}
