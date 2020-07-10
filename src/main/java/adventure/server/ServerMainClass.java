package adventure.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static adventure.utils.Utils.DATABASE_PATH;

/**
 * @author Corona-Extra
 */
public class ServerMainClass {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Connection conn;
        final int port = 10000;

        final String USER = "CREATE TABLE IF NOT EXISTS user (username varchar(30)," +
                "password varchar(100), born_date date, residence varchar(50), PRIMARY KEY (username))";
        final String GAME = "CREATE TABLE IF NOT EXISTS game (id int(10) AUTO_INCREMENT, " +
                "game_saved longblob, game_type int(2), PRIMARY KEY (id))";
        final String USER_GAME = "CREATE TABLE IF NOT EXISTS " +
                "user_game (username varchar(30) references user(username), " +
                "id int(10) AUTO_INCREMENT references game(id), " +
                "PRIMARY KEY (id, username))";

        Statement createUser;
        Statement createGame;
        Statement createUserGame;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The server is running!");
        } catch (IOException e) {
            System.err.println("Cannot start the server!");
        }

        try {
            conn = DriverManager.getConnection(DATABASE_PATH);
            createUser = conn.createStatement();
            createUser.executeUpdate(USER);
            createUser.close();
            createGame = conn.createStatement();
            createGame.executeUpdate(GAME);
            createGame.close();
            createUserGame = conn.createStatement();
            createUserGame.executeUpdate(USER_GAME);
            createUserGame.close();

            while (true) {
                if (serverSocket != null) {
                    socket = serverSocket.accept();
                    Thread thread = new RequestThread(socket, conn);
                    thread.start();
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot start the connection with the client!");
        } catch (SQLException e) {
            System.err.println("Problem SQL Connection!");
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Cannot close the connection with the client!");
            }

        }
    }
}
