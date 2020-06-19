package prisonbreak.parser;

import java.util.HashSet;
import java.util.Set;

public class Token {
    TokenType type;
    Set<String> token = new HashSet<>();

    public Token(TokenType type) {
        this.type = type;
    }

    public Token(TokenType type, Set<String> tokens) {
        this.type = type;
        addAliasToken(tokens);
    }

    public void addAliasToken(String aliasToken) {
        token.add(aliasToken.toLowerCase());
    }

    public void addAliasToken(Set<String> aliasTokens) {
        for (String alias : aliasTokens) {
            addAliasToken(alias);
        }
    }

    public Set<String> getTokens() {
        return token;
    }

    public boolean isToken(String string) {
        return token.contains(string.toLowerCase());
    }
}
