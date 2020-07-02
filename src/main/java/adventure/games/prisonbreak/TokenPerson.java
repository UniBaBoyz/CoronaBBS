package adventure.games.prisonbreak;

import adventure.type.Inventory;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;

import java.util.Set;

public class TokenPerson extends TokenObject {

    private boolean followHero = false;
    private Inventory inventory = new Inventory(3);

    public TokenPerson(int id, String name, Set<String> alias) {
        super(id, name, alias);
    }

    public TokenPerson(int id, String name, Set<String> alias, String description) {
        super(id, name, alias, description);
    }

    public TokenPerson(int id, String name, Set<String> alias, String description, Set<TokenAdjective> adjectives) {
        super(id, name, alias, description, adjectives);
    }

    public boolean isFollowHero() {
        return followHero;
    }

    public void setFollowHero(boolean followHero) {
        this.followHero = followHero;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
