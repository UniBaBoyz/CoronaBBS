package adventure.server;

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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RequestThread extends Thread {
    private final Socket socket;
    private final Connection connectionDb;
    BufferedReader in; // Used to communicate with the client
    PrintWriter out; // Used to communicate with the client
    private GameDescription game;
    private Parser parser;
    private String username;
    boolean exit = false;

    public RequestThread(Socket socket, Connection connDb) {
        this.socket = socket;
        this.connectionDb = connDb;
    }

    public PrintWriter getOutputStreamThread() {
        return out;
    }

    private void initGame() {
        if (game != null && game.getIntroduction() != null) {
            out.println(game.getIntroduction());
            out.println(game.getCurrentRoom().getName() +
                    "\n" + "======================================" +
                    "===========================================\n" +
                    game.getCurrentRoom().getDescription() + "\n" +
                    "=================================================================================\n");
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection" + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            String username;

            /*
            final String NEW_USER = "INSERT INTO users VALUES (?)";
            final String FIND_USER = "select * from users where username = ?";
            boolean notFound = true;
            ResultSet rs;
            PreparedStatement pst;

            // TODO Login Phase
            pst = connectionDb.prepareStatement(FIND_USER);
            username = in.readLine();
            pst.setObject(1, username);
            pst.executeUpdate();
            rs = pst.executeQuery();

            //REGISTRAZIONE
            if (rs.next()) {
                while (notFound) {
                    out.println("Username gia' esistente");
                    username = in.readLine();
                    pst.setObject(1, username);
                    pst.executeUpdate();
                    rs = pst.executeQuery();
                    if (!rs.next()) {
                        notFound = false;
                    }
                }
            } else {
                pst = connectionDb.prepareStatement(NEW_USER);
                pst.setObject(1, username);
                pst.executeUpdate();
                pst.executeQuery();
            }
            pst.close();
             */

            // TODO CHOOSE GAME
            game = new PrisonBreakGame();
            parser = new ParserIta(game.getTokenVerbs(), game.getObjects(), game.getAdjectives());

            // Send Introduction of the game
            initGame();


            //TODO CAMBIARE CONDIZIONE E AGGIUNGERE REGEX PER TERMINARE LA COMUNICAZIONE
            while(true) {
                // Read instruction from the client
                communicateWithTheClient(in.readLine());
            }

        } catch (IOException e) {
            System.err.println("A problem has occured during the communication with the client!");
        } catch (SQLException e) {
            System.err.println("Problem SQL");
        }
            finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Cannot close the communication with the client!");
            }
        }
    }

    private void communicateWithTheClient(String string) {
        if (game != null && parser != null && string != null ) {
            try {
                List<ParserOutput> listParser = parser.parse(string);
                for (ParserOutput p : listParser) {
                    if (p.getVerb() != null && p.getVerb().getVerbType().equals(VerbType.END)
                            && p.getObject().isEmpty()) {
                        out.println("Addio!");
                        break;
                    } else {
                        out.println(game.nextMove(p) + "\n");
                        out.println("====================================================================" +
                                "=============\n");


                        //TODO MANAGE SCORE
                        //view.getTextAreaScore().setText(Integer.toString(game.getScore()));
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
            }
        } else {
            out.println("Addio!");
        }
    }
}
