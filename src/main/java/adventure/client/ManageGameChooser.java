package adventure.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static adventure.Utils.*;

public class ManageGameChooser {
    private final BufferedReader in;
    private final PrintWriter out;
    private final GameChooserView view;
    private ManageGameView gameViewController;

    public ManageGameChooser(BufferedReader in, PrintWriter out, ManageGameView gameViewController) {
        this.in = in;
        this.out = out;
        this.gameViewController = gameViewController;
        view = new GameChooserView(this.gameViewController.getView().getJFmainFrame());
        manageEvent();
        initView();
        this.view.getJDialogMain().setVisible(true);
    }

    public GameChooserView getView() {
        return view;
    }

    public void disposeWindow() {
        view.getJDialogMain().setVisible(false);
        view.getJDialogMain().dispose();
    }

    private void initView() {
        view.setJButtonLoadGame("Carica Partita");
        view.setJButtonNewGame("Nuova Partita");
        view.setJLabelSelectionGame("Seleziona il gioco");
    }

    private void manageEvent() {
        actionListenerWindow();
        actionListenerButtonLoadGame();
        actionListenerButtonNewGame();
    }

    private void actionListenerWindow() {
        view.getJDialogMain().addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(view.getJDialogMain(), "Sei sicuro di voler " +
                        "interrompere la selezione del gioco?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.YES_OPTION) {
                    disposeWindow();
                    gameViewController.disposeWindow();
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void actionListenerButtonLoadGame() {
        view.getJButtonLoadGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prisonBreak = "PrisonBreak";
                String input = view.getJComboBox1().getSelectedItem().toString();
                out.println(LOAD_GAME);
                if (input.equals(prisonBreak)) {
                    out.println(PRISON_BREAK);
                } else {
                    out.println(FIRE_HOUSE);
                }
                try {
                    String response = in.readLine();
                    if (response.equals(NO_GAME_FOUNDED)) {
                        JOptionPane.showMessageDialog(view.getJDialogMain(), "Nessuna partita trovata!",
                                "ERROR!", JOptionPane.ERROR_MESSAGE);
                    } else if (response.equals(GAME_LOADED)) {
                        JOptionPane.showMessageDialog(view.getJDialogMain(), input + " caricato con successo!",
                                "Partita caricata", JOptionPane.INFORMATION_MESSAGE);
                        disposeWindow();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    private void actionListenerButtonNewGame() {
        view.getJButtonNewGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prisonBreak = "PrisonBreak";
                String input = view.getJComboBox1().getSelectedItem().toString();
                out.println(NEW_GAME);
                if (input.equals(prisonBreak)) {
                    out.println(PRISON_BREAK);
                } else {
                    out.println(FIRE_HOUSE);
                }
                try {
                    String response = in.readLine();
                    if (response.equals(NO_GAME_CREATED)) {
                        JOptionPane.showMessageDialog(view.getJDialogMain(), "Nessun gioco creato!",
                                "ERROR!", JOptionPane.ERROR_MESSAGE);
                    } else if (response.equals(GAME_CREATED)) {
                        JOptionPane.showMessageDialog(view.getJDialogMain(), input + " creato!",
                                "Partita creata", JOptionPane.INFORMATION_MESSAGE);
                        disposeWindow();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                disposeWindow();
            }
        });
    }
}
