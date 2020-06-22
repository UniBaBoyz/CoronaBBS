package prisonbreak.parser;

import java.util.HashSet;
import java.util.Set;

public class Token {
    TokenType type;
    Set<String> alias = new HashSet<>();

    public Token(TokenType type) {
        this.type = type;
    }

    public Token(TokenType type, Set<String> tokens) {
        this.type = type;
        addAlias(tokens);
    }

    public void addAlias(String alias) {
        this.alias.add(alias.toLowerCase());
    }

    public void addAlias(Set<String> aliasTokens) {
        for (String alias : aliasTokens) {
            addAlias(alias);
        }
    }

    public Set<String> getAlias() {
        return alias;
    }

    public TokenType getType() {
        return type;
    }

    public boolean isAlias(String alias) {
        return this.alias.contains(alias.toLowerCase());
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", alias=" + alias +
                '}';
    }
}