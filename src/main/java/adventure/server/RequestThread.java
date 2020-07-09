package adventure.server;

import adventure.exceptions.inputException.InputErrorException;
import adventure.exceptions.inputException.LexicalErrorException;
import adventure.exceptions.inputException.SyntaxErrorException;
import adventure.server.games.GameDescription;
import adventure.server.games.firehouse.FireHouseGame;
import adventure.server.games.prisonbreak.PrisonBreakGame;
import adventure.server.parser.Parser;
import adventure.server.parser.ParserIta;
import adventure.server.parser.ParserOutput;
import adventure.utils.Password;
import adventure.utils.Utils;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static adventure.server.games.GameDescription.existingGame;
import static adventure.utils.Games.FIRE_HOUSE_GAME;
import static adventure.utils.Games.PRISON_BREAK_GAME;
import static adventure.utils.Utils.*;

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

                switch (command) {

                    case REGISTRATION:
                        if (!credentials.isEmpty()) {
                            registration(divideCredentials(credentials));
                        }
                        break;

                    case LOGIN:
                        if (!credentials.isEmpty()) {
                            login(divideCredentials(credentials));
                        }
                        break;

                    case NEW_GAME:
                        switch (credentials) {
                            case PRISON_BREAK:
                                gameType = PRISON_BREAK_GAME;
                                if (existingGame(username, gameType)) {
                                    out.println(EXISTING_GAME);
                                    if (in.readLine().equals(YES_CREATE)) {
                                        game = new PrisonBreakGame();
                                    }
                                } else {
                                    game = new PrisonBreakGame();
                                }
                                break;
                            case FIRE_HOUSE:
                                gameType = FIRE_HOUSE_GAME;
                                if (existingGame(username, gameType)) {
                                    out.println(EXISTING_GAME);
                                    if (in.readLine().equals(YES_CREATE)) {
                                        game = new FireHouseGame();
                                    }
                                } else {
                                    game = new FireHouseGame();
                                }
                                break;
                        }

                        if (game != null) {
                            out.println(GAME_CREATED);
                            next = true;
                        }
                        break;

                    case LOAD_GAME:
                        switch (credentials) {

                            case PRISON_BREAK:
                                gameType = PRISON_BREAK_GAME;
                                game = loadGame();
                                break;
                            case FIRE_HOUSE:
                                gameType = FIRE_HOUSE_GAME;
                                game = loadGame();
                                break;
                        }

                        if (game != null) {
                            out.println(GAME_LOADED);
                            next = true;
                        } else {
                            out.println(NO_GAME_FOUNDED);
                        }
                        break;
                    case EXIT:
                        next = true;
                }
            }

            if (game != null) {
                parser = new ParserIta(game.getTokenVerbs(), game.getObjects(), game.getAdjectives());

                // Send Introduction of the game
                initGame();

                do {
                    command = in.readLine();
                    // Read instruction from the client
                    if (!command.equals(EXIT)) {
                        communicateWithTheClient(command);
                    } else {
                        if (in.readLine().equals(OK_EXIT)) {
                            if (in.readLine().equals(SAVE_GAME)) {
                                saveGame();
                            }
                            out.println("Addio!");
                            exit = true;
                        }
                    }
                } while (!command.equals(EXIT) || !exit);
            }

        } catch (IOException e) {
            System.err.println("A problem has occured during the communication with the client!");
        } catch (SQLException e) {
            System.out.println("A problem has occured during the communication with the sql database");
        } finally {
            try {
                System.out.println("Closing thread..." + socket);
                socket.close();
            } catch (IOException e) {
                System.err.println("Cannot close the communication with the client!");
            }
        }
    }

    private void communicateWithTheClient(String string) {
        if (game != null && parser != null && string != null) {
            try {
                List<ParserOutput> listParser = parser.parse(string);
                for (ParserOutput p : listParser) {
                    out.println(game.nextMove(p) + "\n");
                    out.println("====================================================================" +
                            "=============\n");
                    out.println(Utils.SEPARATOR_CHARACTER_STRING + game.getScore());
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

    private void registration(List<String> strings) throws SQLException {
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
                return;
            }

            findUser.close();

            if (!password.matches(PASSWORD_REGEX)) {
                out.println(INVALID_PASSWORD);
                return;
            }

            newUser = connectionDb.prepareStatement(NEW_USER);
            newUser.setString(1, username);
            newUser.setString(2, Password.hashPassword(password));
            newUser.setDate(3, Date.valueOf(bornDate));
            newUser.setString(4, residence);
            newUser.executeUpdate();
            newUser.close();
            out.println(CORRECT_REGISTRATION);
        }
    }

    private void login(List<String> strings) throws SQLException {
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
            return;
        } else {
            if (!Password.checkPass(password, resultUser.getString("password"))) {
                out.println(INVALID_PASSWORD);
                return;
            }
        }

        findUser.close();
        out.println(CORRECT_LOGIN);
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
