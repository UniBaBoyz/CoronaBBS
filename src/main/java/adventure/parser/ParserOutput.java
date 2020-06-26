/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure.parser;

import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenVerb;

/**
 * @author Corona-Extra
 */
public class ParserOutput {
    private TokenVerb verb;
    private TokenObject object;
    private TokenAdjective adjective;

    public ParserOutput(TokenVerb verb) {
        this.verb = verb;
        this.object = null;
        this.adjective = null;
    }

    public ParserOutput(TokenVerb verb, TokenObject object) {
        this.verb = verb;
        this.object = object;
        this.adjective = null;
    }

    public ParserOutput(TokenVerb verb, TokenObject object, TokenAdjective adjective) {
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

    public TokenObject getObject() {
        return object;
    }

    public void setObject(TokenObject object) {
        this.object = object;
    }
}
