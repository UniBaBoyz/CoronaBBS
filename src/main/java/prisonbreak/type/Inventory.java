/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak.type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pierpaolo
 */
public class Inventory {

    private final int MAX_ITEM = 4;

    private List<TokenObject> list = new ArrayList<>();

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
