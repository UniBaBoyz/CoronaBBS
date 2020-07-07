package adventure.server.parser;

import adventure.server.type.TokenObject;
import adventure.server.type.TokenVerb;

import java.util.Set;

/**
 * @author Corona-Extra
 */
public class ParserOutput {
    private TokenVerb verb;
    private Set<TokenObject> object;

    public ParserOutput(TokenVerb verb) {
        this.verb = verb;
        this.object = null;
    }

    public ParserOutput(TokenVerb verb, Set<TokenObject> object) {
        this.verb = verb;
        this.object = object;
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
