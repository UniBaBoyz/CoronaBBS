package adventure.type;

import adventure.parser.Token;
import adventure.parser.TokenType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Corona-Extra
 */
public class TokenObject extends Token {

    private final int id;
    private final Set<TokenAdjective> adjectives = new HashSet<>();
    private String name;
    private String description;
    private boolean openable = false;
    private boolean pickupable = false;
    private boolean giveable = false;
    private boolean mixable = false;
    private boolean usable = false;
    private boolean pushable = false;
    private boolean eatable = false;
    private boolean turnOnAble = false;
    private boolean playable = false;
    private boolean insertable = false;
    private boolean askable = false;
    private boolean sitable = false; //To able to sit down on an object
    private boolean open = false;
    private boolean given = false;
    private boolean used = false;
    private boolean push = false;
    private boolean on = true;
    private boolean taken = false;
    private boolean accept = false;
    private boolean climb = false;
    private boolean sit = false;
    private boolean asked = false;

    public TokenObject(int id, String name, Set<String> alias) {
        super(TokenType.OBJECT);
        this.id = id;
        this.name = name;
        super.setAlias(alias);
    }

    public TokenObject(int id, String name, Set<String> alias, String description) {
        super(TokenType.OBJECT);
        this.id = id;
        this.name = name;
        super.setAlias(alias);
        this.description = description;
    }

    public TokenObject(int id, String name, Set<String> alias, String description, Set<TokenAdjective> adjectives) {
        super(TokenType.OBJECT);
        this.id = id;
        this.name = name;
        super.setAlias(alias);
        this.description = description;
        this.adjectives.addAll(adjectives);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TokenAdjective> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(Set<TokenAdjective> adjectives) {
        this.adjectives.addAll(adjectives);
    }

    public void setAdjective(TokenAdjective adjective) {
        this.adjectives.add(adjective);
    }

    public boolean isOpenable() {
        return openable;
    }

    public void setOpenable(boolean openable) {
        this.openable = openable;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean isAsked() {
        return asked;
    }

    public void setAsked(boolean asked) {
        this.asked = asked;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isTurnOnAble() {
        return turnOnAble;
    }

    public void setTurnOnAble(boolean turnOnAble) {
        this.turnOnAble = turnOnAble;
    }

    public boolean isClimb() {
        return climb;
    }

    public void setClimb(boolean climb) {
        this.climb = climb;
    }

    public boolean isPickupable() {
        return pickupable;
    }

    public void setPickupable(boolean pickupable) {
        this.pickupable = pickupable;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public boolean isPushable() {
        return pushable;
    }

    public void setPushable(boolean pushable) {
        this.pushable = pushable;
    }

    public boolean isSitable() {
        return sitable;
    }

    public void setSitable(boolean sitable) {
        this.sitable = sitable;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isAskable() {
        return askable;
    }

    public void setAskable(boolean askable) {
        this.askable = askable;
    }

    public boolean isMixable() {
        return mixable;
    }

    public void setMixable(boolean mixable) {
        this.mixable = mixable;
    }

    public boolean isEatable() {
        return eatable;
    }

    public void setEatable(boolean eatable) {
        this.eatable = eatable;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean isGiveable() {
        return giveable;
    }

    public void setGiveable(boolean giveable) {
        this.giveable = giveable;
    }

    public boolean isGiven() {
        return given;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public boolean isSit() {
        return sit;
    }

    public void setSit(boolean sit) {
        this.sit = sit;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenObject that = (TokenObject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
