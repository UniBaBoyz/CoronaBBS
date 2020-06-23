/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak.type;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author pierpaolo
 */
public class Room {

    private final int id;
    private final Set<TokenObject> objects = new HashSet<>();
    private String name;
    private String description;
    private String look;
    private boolean visible = true;
    private Room south = null;
    private Room north = null;
    private Room east = null;
    private Room west = null;

    public Room(int id) {
        this.id = id;
    }

    public Room(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Set<TokenObject> getObjects() {
        return objects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public String getLook() {
        return look;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getSouth() {
        return south;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getNorth() {
        return north;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getEast() {
        return east;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public Room getWest() {
        return west;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
