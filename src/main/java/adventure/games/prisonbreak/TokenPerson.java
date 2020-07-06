package adventure.games.prisonbreak;

import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.type.Inventory;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;

import java.util.Set;

/**
 * @author Corona-Extra
 */
public class TokenPerson extends TokenObject {

    private Inventory inventory;
    private boolean followHero = false;
    private boolean speakable = false;

    public TokenPerson(int id, String name, Set<String> alias) {
        super(id, name, alias);
    }

    public TokenPerson(int id, String name, Set<String> alias, String description) {
        super(id, name, alias, description);
    }

    public TokenPerson(int id, String name, Set<String> alias, String description, Set<TokenAdjective> adjectives) {
        super(id, name, alias, description, adjectives);
    }

    public TokenPerson(int id, String name, Set<String> alias, String description,
                       Set<TokenAdjective> adjectives, int dimensionInventory) {
        super(id, name, alias, description, adjectives);
        inventory = new Inventory(dimensionInventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isFollowHero() {
        return followHero;
    }

    public void setFollowHero(boolean followHero) {
        this.followHero = followHero;
    }

    public boolean isSpeakable() {
        return speakable;
    }

    public void setSpeakable(boolean speakable) {
        this.speakable = speakable;
    }

    @Override
    public String getDescription() {
        StringBuilder objects = new StringBuilder(this.getName() + " possiede: ");

        try {
            if (!(getInventory().isEmpty())) {

                for (TokenObject obj : getInventory().getObjects()) {
                    objects.append(obj.getName()).append(", ");
                }
                return super.getDescription() + "\n" + objects.substring(0, objects.length() - 2);
            }
        } catch (InventoryEmptyException ignored) {

        }

        return super.getDescription();
    }
}
