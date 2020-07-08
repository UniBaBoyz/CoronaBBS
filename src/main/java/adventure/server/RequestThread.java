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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

            //registration();
            login();

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
            System.out.println(e);
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
        final String NEW_USER = "INSERT INTO users VALUES (?, ?)";
        final String FIND_USER = "select * from users where username = ?";
        String password;
        boolean notFound = true;
        ResultSet result;
        PreparedStatement findUser;
        PreparedStatement newUser;

        findUser = connectionDb.prepareStatement(FIND_USER);
        username = in.readLine();
        findUser.setString(1, username);
        result = findUser.executeQuery();

        if (result.next()) {
            while (notFound) {
                out.println("Username gia' esistente");
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
        ;

        /*
        while(!password.matches(PASSWORD_REGEX)) {
            password = in.readLine();
        }
        */

        newUser = connectionDb.prepareStatement(NEW_USER);
        newUser.setString(1, username);
        newUser.setObject(2, generateHashedPassword(password));
        newUser.executeUpdate();
        newUser.close();
    }

    public void login() throws SQLException, IOException {
        final String FIND_USER = "select * from users where username = ?";
        final String USER_TRUE = "select password from users where username = ?";
        boolean login = false;
        boolean notFound = true;
        String password;
        ResultSet resultUser;
        ResultSet resultPassword;
        PreparedStatement findUser;
        PreparedStatement userTrue;

        findUser = connectionDb.prepareStatement(FIND_USER);
        username = in.readLine();
        findUser.setString(1, username);
        resultUser = findUser.executeQuery();

        while (!login) {
            if (!resultUser.next()) {
                while (notFound) {
                    out.println("Utente non esistente!");
                    username = in.readLine();
                    findUser.setString(1, username);
                    resultUser = findUser.executeQuery();
                    if (resultUser.next()) {
                        notFound = false;
                    }
                }
                findUser.close();
            } else {
                resultUser = findUser.executeQuery();
                userTrue = connectionDb.prepareStatement(USER_TRUE);
                password = in.readLine();
                userTrue.setObject(1, generateHashedPassword(password));
                resultPassword = userTrue.executeQuery();

                if (resultPassword.next()) {
                    try {
                        game = PrisonBreakGame.loadGame(username);
                        login = true;
                    } catch (ClassNotFoundException e) {
                        out.println("Nessuna partita trovata!");
                    }
                    userTrue.close();
                } else {
                    out.println("Passowrd non corretta!");
                }
            }
        }
    }

    public void saveGame() throws IOException, SQLException {
        if (game != null && username != null) {
            game.saveGame(username);
            out.println("Gioco salvato correttamente!");
        }
    }

    private byte[] generateHashedPassword(String password) {
        byte[] salt = new byte[16];
        MessageDigest md;
        byte[] hashedPassword = new byte[0];
        SecureRandom random = new SecureRandom();

        random.nextBytes(salt);
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }
}
