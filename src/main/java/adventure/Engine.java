package adventure;

//fixme import prisonbreak.games.FireHouseGame;

import adventure.exceptions.inputException.InputErrorException;
import adventure.exceptions.inputException.LexicalErrorException;
import adventure.exceptions.inputException.SyntaxErrorException;
import adventure.games.GameDescription;
import adventure.games.prisonbreak.PrisonBreakGame;
import adventure.parser.Parser;
import adventure.parser.ParserIta;
import adventure.parser.ParserOutput;
import adventure.type.VerbType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

/**
 * ATTENZIONE: l'Engine è molto spartano, in realtà demanda la logica alla
 * classe che implementa GameDescription e si occupa di gestire I/O sul
 * terminale.
 *
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
                    view.getjTextArea1().append("Addio!\n");
                    view.dispose();
                    break;
                } else {
                    view.getjTextArea1().append(game.nextMove(p) + "\n");
                    view.getjTextArea1().append("================================================\n");
                }
            }
        } catch (LexicalErrorException e) {
            view.getjTextArea1().append("Non ho capito!\n");
            view.getjTextArea1().append("C'e' qualche parola che non conosco.\n");
        } catch (SyntaxErrorException e) {
            view.getjTextArea1().append("Non ho capito!\n");
            view.getjTextArea1().append("Dovresti ripassare un po' la grammatica!\n");
        } catch (InputErrorException e) {
            view.getjTextArea1().append("Non ho capito!\n");
        } catch (Exception e) {
            view.getjTextArea1().append(e.toString() + "\n");
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void manageEvent() {
        actionListenerButtonSouth();
        actionListenerButtonInvio();
    }

    private void actionListenerButtonSouth() {
        view.getjButtonSouth().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input("Sud");
            }
        });
    }

    private void actionListenerButtonInvio() {
        view.getButtonInvio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                input(view.getjTextField1().getText());
                view.getjTextField1().setText("");
            }
        });
    }

    public void run() {
        view.getjTextArea1().append(game.getCurrentRoom().getName() + "\n");
        view.getjTextArea1().append("================================================\n");
        view.getjTextArea1().append(game.getCurrentRoom().getDescription() + "\n");
    }

}
