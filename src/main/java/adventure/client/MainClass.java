package adventure.client;
import adventure.server.games.GameDescription;
import adventure.server.games.prisonbreak.PrisonBreakGame;
import adventure.server.parser.Parser;
import adventure.server.parser.ParserIta;

import javax.swing.*;

/**
 * @author Corona-Extra
 */
public class MainClass {
    public static void main(String[] args) {
        GameDescription game = new PrisonBreakGame();
        Parser parser = new ParserIta(game.getTokenVerbs(), game.getObjects(), game.getAdjectives());
        View view = new View();
        Engine engine = new Engine(game, view, parser);

        SwingUtilities.invokeLater(() -> view.getJframe().setVisible(true));

        engine.init();
    }
}
