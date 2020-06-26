/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Corona-Extra
 */
public class TokenObjectContainer extends TokenObject {

    private List<TokenObject> objects = new ArrayList<>();

    public TokenObjectContainer(int id, Set<String> alias) {
        super(id, alias);
    }

    public TokenObjectContainer(int id, Set<String> alias, String description) {
        super(id, alias, description);
    }

    public TokenObjectContainer(int id, Set<String> alias, String description, Set<TokenAdjective> adjectives) {

        super(id, alias, description, adjectives);
    }

    public List<TokenObject> getObjects() {
        return objects;
    }

    public void setObjects(List<TokenObject> objects) {
        this.objects = objects;
    }

    public boolean containsObject(TokenObject object) {
        return getObjects().contains(object);
    }

    public void add(TokenObject o) {
        objects.add(o);
    }

    public void remove(TokenObject o) {
        objects.remove(o);
    }

}
