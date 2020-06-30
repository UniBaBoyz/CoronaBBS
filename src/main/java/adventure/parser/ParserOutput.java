package adventure.parser;

import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenVerb;

import java.util.Set;

/**
 * @author Corona-Extra
 */
public class ParserOutput {
    private TokenVerb verb;
    private Set<TokenObject> object;
    private TokenAdjective adjective;

    public ParserOutput(TokenVerb verb) {
        this.verb = verb;
        this.object = null;
        this.adjective = null;
    }

    public ParserOutput(TokenVerb verb, Set<TokenObject> object) {
        this.verb = verb;
        this.object = object;
        this.adjective = null;
    }

    public ParserOutput(TokenVerb verb, Set<TokenObject> object, TokenAdjective adjective) {
        this.verb = verb;
        this.object = object;
        this.adjective = adjective;
    }

    public TokenVerb getVerb() {
        return verb;
    }

    public void setVerb(TokenVerb verb) {
        this.verb = verb;
    }

    public Set<TokenObject> getObject() {
        return object;
    }

    public void setObject(Set<TokenObject> object) {
        this.object = object;
    }
}
