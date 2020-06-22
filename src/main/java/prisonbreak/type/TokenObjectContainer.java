/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author pierpaolo
 */
public class TokenObjectContainer extends TokenObject {

    private List<TokenObject> list = new ArrayList<>();

    public TokenObjectContainer(int id) {
        super(id);
    }

    public TokenObjectContainer(int id, String name) {
        super(id, name);
    }

    public TokenObjectContainer(int id, String name, String description) {
        super(id, name, description);
    }

    public TokenObjectContainer(int id, String name, String description, Set<String> alias) {
        super(id, name, description, alias);
    }

    public List<TokenObject> getList() {
        return list;
    }

    public void setList(List<TokenObject> list) {
        this.list = list;
    }

    public void add(TokenObject o) {
        list.add(o);
    }

    public void remove(TokenObject o) {
        list.remove(o);
    }

}
