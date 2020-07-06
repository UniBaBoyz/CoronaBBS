package adventure;
import adventure.games.prisonbreak.PrisonBreakGame;
import adventure.parser.Parser;
import adventure.parser.ParserIta;

import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        PrisonBreakGame game = new PrisonBreakGame();
        Parser parser = new ParserIta(game.getTokenVerbs(), game.getObjects(), game.getAdjectives());
        View view = new View();
        Engine engine = new Engine(game, view, parser);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.setVisible(true);
            }
        });

        engine.run();
    }
}
