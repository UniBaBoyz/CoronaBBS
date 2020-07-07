package adventure;

import adventure.exceptions.inputException.InputErrorException;
import adventure.exceptions.inputException.LexicalErrorException;
import adventure.exceptions.inputException.SyntaxErrorException;
import adventure.games.GameDescription;
import adventure.parser.Parser;
import adventure.parser.ParserOutput;
import adventure.type.VerbType;

import javax.swing.*;
import java.util.List;

/**
 * @author Corona-Extra
 */
public class Engine {
    private final GameDescription game;
    private final View view;
    private final Parser parser;

    public Engine(GameDescription game, View view, Parser parser) {
        this.game = game;
        this.view = view;
        this.parser = parser;
        this.view.setTitle(this.game.getTitle());
        manageEvent();
    }

    public Parser getParser() {
        return parser;
    }

    private void input(String string) {
        try {
            List<ParserOutput> listParser = parser.parse(string);
            for (ParserOutput p : listParser) {
                if (p.getVerb() != null && p.getVerb().getVerbType().equals(VerbType.END)
                        && p.getObject().isEmpty()) {
                    int input = JOptionPane.showConfirmDialog(view.getJframe(), "Sei sicuro di voler uscire dal gioco?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (input == JOptionPane.YES_OPTION) {
                        view.getOutputArea().append("Addio!");
                        view.getJframe().dispose();
                        break;
                    }
                } else if (p.getVerb() != null && p.getVerb().getVerbType().equals(VerbType.INVENTORY)
                        && p.getObject().isEmpty()) {
                    JOptionPane.showMessageDialog(view.getJframe(), game.nextMove(p), "INVENTARIO", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    view.getOutputArea().append(game.nextMove(p) + "\n");
                    view.getOutputArea().append("====================================================================" +
                            "=============\n");
                    view.getTextAreaScore().setText(Integer.toString(game.getScore()));
                }
            }
        } catch (LexicalErrorException e) {
            view.getOutputArea().append("Non ho capito!\n");
            view.getOutputArea().append("C'e' qualche parola che non conosco.\n");
            view.getOutputArea().append("=============================================================================" +
                    "====\n");
        } catch (SyntaxErrorException e) {
            view.getOutputArea().append("Non ho capito!\n");
            view.getOutputArea().append("Dovresti ripassare un po' la grammatica!\n");
            view.getOutputArea().append("=============================================================================" +
                    "====\n");
        } catch (InputErrorException e) {
            view.getOutputArea().append("Non ho capito!\n");
            view.getOutputArea().append("=============================================================================" +
                    "====\n");
        } catch (Exception e) {
            view.getOutputArea().append(e.toString() + "\n");
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void manageEvent() {
        actionListenerButtonNorth();
        actionListenerButtonSouth();
        actionListenerButtonEast();
        actionListenerButtonWest();
        actionListenerButtonInventory();
        actionListenerButtonLook();
        actionListenerButtonEnter();
        actionListenerInputField();
    }

    private void actionListenerButtonNorth() {
        view.getButtonNorth().addActionListener(e -> input("Nord"));
    }

    private void actionListenerButtonSouth() {
        view.getButtonSouth().addActionListener(e -> input("Sud"));
    }

    private void actionListenerButtonEast() {
        view.getButtonEast().addActionListener(e -> input("Est"));
    }

    private void actionListenerButtonWest() {
        view.getButtonWest().addActionListener(e -> input("Ovest"));
    }

    private void actionListenerButtonInventory() {
        view.getButtonInventory().addActionListener(e -> input("Inventario"));
    }

    private void actionListenerButtonLook() {
        view.getButtonLook().addActionListener(e -> input("Guarda"));
    }

    private void actionListenerButtonEnter() {
        view.getButtonEnter().addActionListener(ev -> {
            input(view.getInputField().getText());
            view.getInputField().setText("");
        });
    }

    private void actionListenerInputField() {
        view.getInputField().addActionListener(ev -> {
            input(view.getInputField().getText());
            view.getInputField().setText("");
        });
    }

    public void init() {
        if (game.getIntroduction() != null) {
            view.getOutputArea().append(game.getIntroduction());
        }

        view.getOutputArea().append(game.getCurrentRoom().getName() +
                "\n" + "======================================" +
                "===========================================\n" +
                game.getCurrentRoom().getDescription() + "\n" +
                "=================================================================================\n");
        view.getTextAreaScore().setText(Integer.toString(game.getScore()));
    }

}
