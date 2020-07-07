package adventure.server.games;

import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.exceptions.objectsException.ObjectsAmbiguityException;
import adventure.exceptions.objectsException.ObjectsException;
import adventure.server.User;
import adventure.server.parser.ParserOutput;
import adventure.server.type.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.*;

/**
 * @author Corona-Extra
 */
public abstract class GameDescription {
    private final String title;
    private String introduction;
    private static final int INCREASE_SCORE = 10;
    private final ObjectsInterface gameObjects;
    private final RoomsInterface gameRooms;
    private final VerbsInterface gameVerbs;
    private final Set<Room> rooms = new HashSet<>();
    private final Set<TokenVerb> tokenVerbs = new HashSet<>();
    private final Set<TokenObject> objectNotAssignedRoom = new HashSet<>();
    private Inventory inventory;
    private Room currentRoom;
    private int score = 0;
    private Connection conn;

    public GameDescription(ObjectsInterface gameObjects, RoomsInterface gameRooms, VerbsInterface gameVerbs, String title) throws SQLException {
        this.title = title;
        this.gameRooms = gameRooms;
        this.gameObjects = gameObjects;
        this.gameVerbs = gameVerbs;
        connect();
        init();
    }

    public static GameDescription loadGame() throws IOException, ClassNotFoundException, SQLException {
        GameDescription game = null;
        PreparedStatement ps;
        ResultSet rs;
        String sql;

        sql = "select * from games where id=1";

        ps = conn.prepareStatement(sql);

        rs = ps.executeQuery();

        if (rs.next()) {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            try {

                bais = new ByteArrayInputStream(rs.getBytes("javaObject"));

                ins = new ObjectInputStream(bais);

                MyClass mc = (MyClass) ins.readObject();

                System.out.println("Object in value ::" + mc.getSName());
                ins.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        return rmObj;
    }

    private void init() {
        getGameVerbs().initVerbs(this);
        getGameRooms().initRooms(this);
        getGameObjects().initObjects(this);
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public ObjectsInterface getGameObjects() {
        return gameObjects;
    }

    public RoomsInterface getGameRooms() {
        return gameRooms;
    }

    public VerbsInterface getGameVerbs() {
        return gameVerbs;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int id) {
        return rooms.stream().filter(room -> room.getId() == id).findFirst().orElse(null);
    }

    public Set<TokenVerb> getTokenVerbs() {
        return tokenVerbs;
    }

    public Set<TokenObject> getObjectNotAssignedRoom() {
        return objectNotAssignedRoom;
    }

    public void setObjectNotAssignedRoom(Set<TokenObject> objectNotAssignedRoom) {
        this.objectNotAssignedRoom.addAll(objectNotAssignedRoom);
    }

    public void setObjectNotAssignedRoom(TokenObject objectNotAssignedRoom) {
        this.objectNotAssignedRoom.add(objectNotAssignedRoom);
    }

    public void removeObjectNotAssigned(TokenObject object) {
        objectNotAssignedRoom.remove(object);
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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public abstract String nextMove(ParserOutput p);

    public Set<TokenObject> getObjects() {
        Set<TokenObject> objects = new HashSet<>(objectNotAssignedRoom);

        try {
            objects.addAll(getInventory().getObjects());
        } catch (InventoryEmptyException ignored) {

        }

        if (!rooms.isEmpty()) {
            for (Room i : rooms) {
                for (TokenObject o : i.getObjects()) {
                    objects.add(o);

                    if (o instanceof TokenObjectContainer) {
                        objects.addAll(((TokenObjectContainer) o).getObjects());
                    }
                }
            }
        }

        return objects;
    }

    public TokenObject getObject(int id) {
        return getObjects().stream().filter(tokenObject -> tokenObject.getId() == id).findFirst().orElse(null);
    }

    public Set<TokenAdjective> getAdjectives() {
        Set<TokenAdjective> adjectives = new HashSet<>();
        Set<TokenObject> objects = new HashSet<>(getObjects());

        if (!objects.isEmpty()) {
            for (TokenObject i : objects) {
                adjectives.addAll(i.getAdjectives());
            }
        }

        return adjectives;
    }

    public void increaseScore() {
        this.score += INCREASE_SCORE;
    }

    public int getScore() {
        return score;
    }

    public TokenObject getCorrectObject(Set<TokenObject> tokenObjects) throws ObjectsException {
        long countObjectRoomAndInventory = tokenObjects.stream()
                .filter(o -> (getCurrentRoom().containsObject(o) || getInventory().contains(o))).count();
        TokenObject correctObject = null;

        if (countObjectRoomAndInventory > 1) {
            throw new ObjectsAmbiguityException();
        } else if (countObjectRoomAndInventory == 1) {
            correctObject = tokenObjects.stream()
                    .filter(o -> (getCurrentRoom().containsObject(o) || getInventory().contains(o)))
                    .findFirst()
                    .orElse(null);
        } else if (tokenObjects.stream()
                .filter(o -> getObjectNotAssignedRoom().contains(o)).count() >= 1) {
            correctObject = tokenObjects.stream()
                    .filter(o -> getObjectNotAssignedRoom().contains(o))
                    .findFirst()
                    .orElse(null);
        } else if (tokenObjects.stream()
                .noneMatch(object -> getCurrentRoom().containsObject(object)
                        || getInventory().contains(object) || getObjectNotAssignedRoom().contains(object))
                && !tokenObjects.isEmpty()) {
            throw new ObjectNotFoundInRoomException();
        }

        return correctObject;
    }

    public void createRooms(Room root) {
        Set<Room> visited = new LinkedHashSet<>();
        Queue<Room> queue = new LinkedList<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Room vertex = queue.poll();
            List<Room> link = new ArrayList<>();
            if (vertex.getNorth() != null) {
                link.add(vertex.getNorth());
            }
            if (vertex.getSouth() != null) {
                link.add(vertex.getSouth());
            }
            if (vertex.getEast() != null) {
                link.add(vertex.getEast());
            }
            if (vertex.getWest() != null) {
                link.add(vertex.getWest());
            }
            for (Room v : link) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                }
            }
        }

        rooms.addAll(visited);
    }

    private void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:/C://Utenti/user/Desktop/database");
    }

    public void saveGame(User user) {
        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS games ('User' longblob NOT NULL AUTO_INCREMENT, " +
                "'GameDescription' longblob, PRIMARY KEY (id))";
        PreparedStatement ps;
        String sql;

        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate(CREATE_TABLE);
            stm.close();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(this);
            oos.flush();
            oos.close();
            bos.close();
            byte[] game = bos.toByteArray();

            sql = "insert into games (GameDescription) values(?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(user, game);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
