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

    public TokenObjectContainer(int id, Set<String> alias) {
        super(id, alias);
    }

    public TokenObjectContainer(int id, Set<String> alias, String description) {
        super(id, alias, description);
    }

    public TokenObjectContainer(int id, Set<String> alias, String description, Set<TokenAdjective> adjectives) {

        super(id, alias, description, adjectives);
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
