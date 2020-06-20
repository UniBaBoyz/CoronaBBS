/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak.type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import prisonbreak.parser.Token;
import prisonbreak.parser.TokenType;

/**
 * @author pierpaolo
 */
public class TokenVerb extends Token {
    private final String name;
    private Set<String> alias;

    public TokenVerb(String name, Set<String> alias) {
        super(TokenType.VERB, alias);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.getType());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final TokenVerb other = (TokenVerb) obj;
        if (this.getType() != other.getType()
                || this.getName() != other.getName()
                || this.getAlias() != other.getAlias()) {
            return false;
        }
        return true;
    }

}
