/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak.parser;

import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;

/**
 * @author pierpaolo
 */
public class ParserOutput {

    private TokenVerb verb;
    private TokenObject object;

    public ParserOutput(TokenVerb verb) {
        this.verb = verb;
        this.object = null;
    }

    public ParserOutput(TokenVerb verb, TokenObject object) {
        this.verb = verb;
        this.object = object;
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

    @Override
    public String toString() {
        return "ParserOutput{" +
                "verb=" + verb +
                ", object=" + object +
                '}';
    }
}
