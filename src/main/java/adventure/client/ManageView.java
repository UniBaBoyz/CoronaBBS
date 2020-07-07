package adventure.client;

import adventure.exceptions.inputException.InputErrorException;
import adventure.exceptions.inputException.LexicalErrorException;
import adventure.exceptions.inputException.SyntaxErrorException;
import adventure.server.games.GameDescription;
import adventure.server.parser.Parser;
import adventure.server.parser.ParserOutput;
import adventure.server.type.VerbType;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Corona-Extra
 */
public class ManageView {
    private final BufferedReader in;
    private final PrintWriter out;

    private final View view = new View();

    public ManageView(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        initView();
        view.setVisibleWindow(true);
    }

    private void initView() {
        view.setButtonLook("Guarda");
        view.setButtonInventory("Inventario");
        view.setButtonEnter("Invio");
        view.setButtonNorth("Nord");
        view.setButtonSouth("Sud");
        view.setButtonEast("Est");
        view.setButtonWest("Ovest");
        view.setLabelScore("Score");
    }


}
