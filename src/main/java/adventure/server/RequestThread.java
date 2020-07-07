package adventure.server;

import adventure.games.GameDescription;
import adventure.games.prisonbreak.PrisonBreakGame;
import adventure.games.firehouse.FireHouseGame;
import adventure.parser.Parser;
import adventure.parser.ParserIta;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class RequestThread extends Thread {
    private final Socket socket;
    BufferedReader in; // Used to communicate with the client
    PrintWriter out; // Used to communicate with the client
    private String username;
    boolean exit = false;
    private static final Map<String, Long> usernameTaken = Collections.synchronizedMap(new HashMap<>());

    public RequestThread(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getOutputStreamThread() {
        return out;
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection" + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            // TODO Login Phase

            // TODO CHOOSE GAME
            GameDescription game = new PrisonBreakGame();
            Parser parser = new ParserIta(game.getTokenVerbs(), game.getObjects(), game.getAdjectives());

            in.readLine();
            out.println(game.nextMove());

        } catch (IOException e) {
            System.err.println("A problem has occured during the communication with the client!");
            if (username != null) {
                usernameTaken.remove(username);
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Cannot close the communication with the client!");
            }
        }
    }

    public RequestThread findThread(String username) {
        RequestThread thread = null;

        long idThread = usernameTaken.get(username);
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getId() == idThread) {
                thread = (RequestThread) t;
            }
        }

        return thread;
    }
}
