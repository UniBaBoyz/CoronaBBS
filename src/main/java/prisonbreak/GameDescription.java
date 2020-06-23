/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import prisonbreak.parser.ParserOutput;
import prisonbreak.type.Inventory;
import prisonbreak.type.Room;
import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;

/**
 * @author pierpaolo
 */
public abstract class GameDescription {

    private final Set<Room> rooms = new HashSet<>();

    private final Set<TokenVerb> tokenVerbs = new HashSet<>();

    private Inventory inventory;

    private Room currentRoom;

    public Set<Room> getRooms() {
        return rooms;
    }

    public Set<TokenVerb> getTokenVerbs() {
        return tokenVerbs;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public abstract void init() throws Exception;

    public abstract void nextMove(ParserOutput p, PrintStream out);

    public Set<TokenObject> getObjects() {
        Set<TokenObject> objects = new HashSet<>();

        if (!rooms.isEmpty()) {
            for (Room i : rooms) {
                objects.addAll(i.getObjects());
            }
        }

        return objects;
    }

    public Set<String> getAdjectives() {
        Set<String> adjectives = new HashSet<>();
        Set<TokenObject> objects;

        if (getObjects() != null) {
            objects = getObjects();

            for (TokenObject i : objects) {
                adjectives.addAll(i.getAdjectives());
            }
        }

        return adjectives;
    }

}
