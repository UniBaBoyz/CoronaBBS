/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak.parser;

import prisonbreak.type.AdvObject;
import prisonbreak.type.TokenVerb;

/**
 * @author pierpaolo
 */
public class ParserOutput {

    private TokenVerb verb;
    private AdvObject object;
    private AdvObject invObject;

    public ParserOutput(TokenVerb verb, AdvObject object) {
        this.verb = verb;
        this.object = object;
    }

    public ParserOutput(TokenVerb verb, AdvObject object, AdvObject invObejct) {
        this.verb = verb;
        this.object = object;
        this.invObject = invObejct;
    }

    public TokenVerb getVerb() {
        return verb;
    }

    public void setVerb(TokenVerb verb) {
        this.verb = verb;
    }

    public AdvObject getObject() {
        return object;
    }

    public void setObject(AdvObject object) {
        this.object = object;
    }

    public AdvObject getInvObject() {
        return invObject;
    }

    public void setInvObject(AdvObject invObject) {
        this.invObject = invObject;
    }

}
