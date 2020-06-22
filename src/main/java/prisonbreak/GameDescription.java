/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak;

import prisonbreak.parser.ParserOutput;
import prisonbreak.type.Inventory;
import prisonbreak.type.Room;
import prisonbreak.type.TokenVerb;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pierpaolo
 */
public abstract class GameDescription {

    private final List<Room> rooms = new ArrayList<>();

    private final List<TokenVerb> tokenVerbs = new ArrayList<>();

    private Inventory inventory;

    private Room currentRoom;

    public List<Room> getRooms() {
        return rooms;
    }

    public List<TokenVerb> getTokenVerbs() {
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

}
