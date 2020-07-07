package adventure.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerMainClass {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Connection conn = null;
        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id int(10) NOT NULL AUTO_INCREMENT, " +
                "username varchar(50), PRIMARY KEY (id, username))";

        final int port = 10000;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The server is running!");
        } catch (IOException e) {
            System.err.println("Cannot start the server!");
        }

        try {
            conn = DriverManager.getConnection("jdbc:h2:./database/users");
            Statement stm = conn.createStatement();
            stm.executeUpdate(CREATE_TABLE);
            stm.close();

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
