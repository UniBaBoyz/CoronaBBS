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

    private List<AdvObject> list = new ArrayList<>();

    public List<AdvObject> getList() {
        return list;
    }

    public void setList(List<AdvObject> list) {
        this.list = list;
    }

    public void add(AdvObject o) {
        list.add(o);
    }

    public void remove(AdvObject o) {
        list.remove(o);
    }
}
