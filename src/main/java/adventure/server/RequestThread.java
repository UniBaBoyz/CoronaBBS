package adventure.server;

import adventure.Utils;
import adventure.exceptions.inputException.InputErrorException;
import adventure.exceptions.inputException.LexicalErrorException;
import adventure.exceptions.inputException.SyntaxErrorException;
import adventure.server.games.GameDescription;
import adventure.server.games.firehouse.FireHouseGame;
import adventure.server.games.prisonbreak.PrisonBreakGame;
import adventure.server.parser.Parser;
import adventure.server.parser.ParserIta;
import adventure.server.parser.ParserOutput;
import adventure.server.type.VerbType;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static adventure.Games.FIRE_HOUSE_GAME;
import static adventure.Games.PRISON_BREAK_GAME;
import static adventure.Utils.*;

/**
 * @author Corona-Extra
 */
public class RequestThread extends Thread {
    private final Socket socket;
    private final Connection connectionDb;
    BufferedReader in; // Used to communicate with the client
    PrintWriter out; // Used to communicate with the client
    boolean exit = false;
    private GameDescription game = null;
    private Parser parser;
    private String username;
    private int gameType;

    public RequestThread(Socket socket, Connection connDb) {
        this.socket = socket;
        this.connectionDb = connDb;
    }

    public PrintWriter getOutputStreamThread() {
        return out;
    }

    private void initGame() {
        if (game != null) {
            if (game.getIntroduction() != null) {
                out.println(game.getIntroduction());
            }
            out.println(game.getCurrentRoom().getName() +
                    "\n" + "======================================" +
                    "===========================================\n" +
                    game.getCurrentRoom().getDescription() + "\n" +
                    "=================================================================================\n");
            out.println(Utils.SEPARATOR_CHARACTER_STRING + game.getScore());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection" + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            boolean next = false;
            String command;
            String credentials;

            while (!next) {
                command = in.readLine();
                credentials = in.readLine();
                if (command.matches(REGISTRATION)) {
                    if (!credentials.isEmpty()) {
                        registration(divideCredentials(credentials));
                    }
                } else if (command.matches(LOGIN)) {
                    if (!credentials.isEmpty()) {
                        login(divideCredentials(credentials));
                    }
                } else if (command.matches(NEW_GAME)) {
                    if (credentials.matches(PRISON_BREAK)) {
                        gameType = PRISON_BREAK_GAME;
                        game = new PrisonBreakGame();
                    } else if (credentials.matches(FIRE_HOUSE)) {
                        gameType = FIRE_HOUSE_GAME;
                        game = new FireHouseGame();
                    }
                    if (game != null) {
                        out.println(GAME_CREATED);
                        next = true;
                    } else {
                        out.println(NO_GAME_CREATED);
                    }
                } else if (command.matches(LOAD_GAME)) {
                    if (credentials.matches(PRISON_BREAK)) {
                        gameType = PRISON_BREAK_GAME;
                        game = loadGame();
                    } else if (credentials.matches(FIRE_HOUSE)) {
                        gameType = FIRE_HOUSE_GAME;
                        game = loadGame();
                    }
                    if (game != null) {
                        out.println(GAME_LOADED);
                        next = true;
                    } else {
                        out.println(NO_GAME_FOUNDED);
                    }
                }
            }

            assert game != null;
            parser = new ParserIta(game.getTokenVerbs(), game.getObjects(), game.getAdjectives());

            // Send Introduction of the game
            initGame();

            //TODO CAMBIARE CONDIZIONE E AGGIUNGERE REGEX PER TERMINARE LA COMUNICAZIONE
            while (true) {
                // Read instruction from the client
                communicateWithTheClient(in.readLine());
            }

        } catch (IOException e) {
            System.err.println("A problem has occured during the communication with the client!");
        } catch (SQLException e) {
            System.out.println("A problem has occured during the communication with the sql database");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Cannot close the communication with the client!");
            }
        }
    }

    private void communicateWithTheClient(String string) {
        if (game != null && parser != null && string != null) {
            try {
                List<ParserOutput> listParser = parser.parse(string);
                for (ParserOutput p : listParser) {
                    if (p.getVerb() != null && p.getVerb().getVerbType().equals(VerbType.END)
                            && p.getObject().isEmpty()) {
                        saveGame();
                        out.println("Addio!");
                        break;
                    } else {
                        out.println(game.nextMove(p) + "\n");
                        out.println("====================================================================" +
                                "=============\n");
                        out.println(Utils.SEPARATOR_CHARACTER_STRING + game.getScore());
                    }
                }
            } catch (LexicalErrorException e) {
                out.println("Non ho capito!\n");
                out.println("C'e' qualche parola che non conosco.\n");
                out.println("=============================================================================" +
                        "====\n");
            } catch (SyntaxErrorException e) {
                out.println("Non ho capito!\n");
                out.println("Dovresti ripassare un po' la grammatica!\n");
                out.println("=============================================================================" +
                        "====\n");
            } catch (InputErrorException e) {
                out.println("Non ho capito!\n");
                out.println("=============================================================================" +
                        "====\n");
            } catch (Exception e) {
                out.println("C'e' qualcosa che non va" + "\n");
                out.println("=============================================================================" +
                        "====\n");
                out.println(e);
            }
        } else {
            out.println("Addio!");
        }
    }

    private List<String> divideCredentials(String string) {
        return new ArrayList<>(Arrays.asList(string.split(SEPARATOR_CHARACTER_STRING)));
    }

    private boolean registration(List<String> strings) throws SQLException {
        final String NEW_USER = "insert into user values (?, ?, ?, ?)";
        final String FIND_USER = "select * from user where username = ?";
        String password;
        String bornDate;
        String residence;
        ResultSet result;
        PreparedStatement findUser;
        PreparedStatement newUser;

        if (strings.size() == 4) {
            username = strings.get(0);
            password = strings.get(1);
            bornDate = strings.get(2);
            residence = strings.get(3);
            findUser = connectionDb.prepareStatement(FIND_USER);
            findUser.setString(1, username);
            result = findUser.executeQuery();

            if (result.next()) {
                out.println(EXISTING_USERNAME);
                return false;
            }

            findUser.close();

            if (!password.matches(PASSWORD_REGEX)) {
                out.println(INVALID_PASSWORD);
                return false;
            }

            newUser = connectionDb.prepareStatement(NEW_USER);
            newUser.setString(1, username);
            newUser.setString(2, Password.hashPassword(password));
            newUser.setDate(3, Date.valueOf(bornDate));
            newUser.setString(4, residence);
            newUser.executeUpdate();
            newUser.close();
            out.println(CORRECT_REGISTRATION);
            return true;
        } else {
            return false;
        }
    }

    private boolean login(List<String> strings) throws SQLException {
        final String FIND_USER = "select * from user where username = ?";
        String password = strings.get(1);
        ResultSet resultUser;
        PreparedStatement findUser;

        username = strings.get(0);
        findUser = connectionDb.prepareStatement(FIND_USER);
        findUser.setString(1, username);
        resultUser = findUser.executeQuery();

        if (!resultUser.next()) {
            out.println(NON_EXISTING_USER);
            return false;
        } else {
            if (!Password.checkPass(password, resultUser.getString("password"))) {
                out.println(INVALID_PASSWORD);
                return false;
            }
        }

        findUser.close();
        out.println(CORRECT_LOGIN);
        return true;
    }

    private GameDescription loadGame() throws SQLException, IOException {
        try {
            return GameDescription.loadGame(username, gameType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveGame() throws IOException, SQLException {
        if (game != null && username != null) {
            game.saveGame(username, gameType);
            out.println(GAME_SAVED);
        }
    }
}
