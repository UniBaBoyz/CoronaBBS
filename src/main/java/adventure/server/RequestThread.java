package adventure.server;

import adventure.Utils;
import adventure.exceptions.inputException.InputErrorException;
import adventure.exceptions.inputException.LexicalErrorException;
import adventure.exceptions.inputException.SyntaxErrorException;
import adventure.server.games.GameDescription;
import adventure.server.games.prisonbreak.PrisonBreakGame;
import adventure.server.parser.Parser;
import adventure.server.parser.ParserIta;
import adventure.server.parser.ParserOutput;
import adventure.server.type.VerbType;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.List;

import static adventure.Utils.PASSWORD_REGEX;
import static adventure.Utils.REGISTRATION;

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
                out.println(game.getCurrentRoom().getName() +
                        "\n" + "======================================" +
                        "===========================================\n" +
                        game.getCurrentRoom().getDescription() + "\n" +
                        "=================================================================================\n");
            }
            out.println(Utils.SEPARATOR_CHARACTER_STRING + game.getScore());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection" + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            if (in.readLine().matches(REGISTRATION)) {
                registration();
            } else {
                login();
            }

            // TODO CHOOSE GAME
            if (game == null) {
                game = new PrisonBreakGame();
            }
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

    private void registration() throws SQLException, IOException {
        final String NEW_USER = "insert into user values (?, ?, ?, ?)";
        final String FIND_USER = "select * from user where username = ?";
        boolean notFound = true;
        String password;
        String bornDate;
        String residence;
        ResultSet result;
        PreparedStatement findUser;
        PreparedStatement newUser;

        findUser = connectionDb.prepareStatement(FIND_USER);
        username = in.readLine();
        findUser.setString(1, username);
        result = findUser.executeQuery();

        if (result.next()) {
            while (notFound) {
                out.println("#existing_username");
                username = in.readLine();
                findUser.setString(1, username);
                result = findUser.executeQuery();
                if (!result.next()) {
                    notFound = false;
                }
            }
            findUser.close();
        }

        password = in.readLine();
        bornDate = in.readLine();
        residence = in.readLine();

        while (!password.matches(PASSWORD_REGEX)) {
            password = in.readLine();
            out.println("#not_match_regex");
        }

        newUser = connectionDb.prepareStatement(NEW_USER);
        newUser.setString(1, username);
        newUser.setString(2, Password.hashPassword(password));
        newUser.setDate(3, Date.valueOf(bornDate));
        newUser.setString(4, residence);
        newUser.executeUpdate();
        newUser.close();
    }

    public void login() throws SQLException, IOException {
        final String FIND_USER = "select * from user where username = ?";
        boolean login = false;
        boolean notFound = true;
        String password;
        ResultSet resultUser;
        PreparedStatement findUser;

        findUser = connectionDb.prepareStatement(FIND_USER);
        username = in.readLine();
        findUser.setString(1, username);
        resultUser = findUser.executeQuery();

        while (!login) {
            if (!resultUser.next()) {
                while (notFound) {
                    out.println("incorrect_username");
                    username = in.readLine();
                    findUser.setString(1, username);
                    resultUser = findUser.executeQuery();
                    if (resultUser.next()) {
                        notFound = false;
                        resultUser = findUser.executeQuery();
                    }
                }
            } else {
                resultUser = findUser.executeQuery();
                resultUser.next();
                password = in.readLine();

                if (Password.checkPass(password, resultUser.getString("password"))) {
                    game = loadGame();
                    login = true;
                    findUser.close();
                } else {
                    out.println("#incorrect_password");
                }
            }
        }
    }

    public GameDescription loadGame() throws SQLException, IOException {
        try {
            return GameDescription.loadGame(username, gameType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveGame() throws IOException, SQLException {
        if (game != null && username != null) {
            game.saveGame(username, gameType);
            out.println("#game_saved");
        }
    }
}
