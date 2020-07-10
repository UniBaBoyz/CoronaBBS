package adventure.server.parser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Corona-Extra
 */
public class Token implements Serializable {
    private final TokenType type;
    private final Set<String> alias = new HashSet<>();

    public Token(TokenType type) {
        this.type = type;
    }

    public Token(TokenType type, Set<String> tokens) {
        this.type = type;
        setAlias(tokens);
    }

    public Set<String> getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias.add(alias.toLowerCase());
    }

    public void setAlias(Set<String> aliasTokens) {
        for (String alias : aliasTokens) {
            setAlias(alias);
        }
    }

    public TokenType getType() {
        return type;
    }

    public boolean isAlias(String alias) {
        return this.alias.contains(alias.toLowerCase());
    }
}
