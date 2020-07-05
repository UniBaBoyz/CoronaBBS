package adventure.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Corona-Extra
 */
public class TokenObjectContainer extends TokenObject {

    private List<TokenObject> objects = new ArrayList<>();

    public TokenObjectContainer(int id, String name, Set<String> alias) {
        super(id, name, alias);
    }

    public TokenObjectContainer(int id, String name, Set<String> alias, String description) {
        super(id, name, alias, description);
    }

    public TokenObjectContainer(int id, String name, Set<String> alias, String description, Set<TokenAdjective> adjectives) {
        super(id, name, alias, description, adjectives);
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
