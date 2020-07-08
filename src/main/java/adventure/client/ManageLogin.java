package adventure.client;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ManageLogin {
    private final BufferedReader in;
    private final PrintWriter out;
    private final LoginView view;
    private ManageGameView gameViewController;

    public ManageLogin(BufferedReader in, PrintWriter out, ManageGameView gameViewController) {
        this.in = in;
        this.out = out;
        this.gameViewController = gameViewController;
        view = new LoginView(this.gameViewController.getView().getJFmainFrame());
        manageEvent();
        initView();
    }

    public void run() throws IOException {
        // TODO Cambiare condizione
        while (true) {
            manageInput(in.readLine());
        }
    }

    public void disposeWindow() {
        view.getJDialogMain().setVisible(false);
        view.getJDialogMain().dispose();
    }

    private void manageInput(String string) {

    }

    private void initView() {
        view.setJLabelUsername("Username");
        view.setJLabelPassword("Password");
        view.setJButtonLogin("Accedi");
        view.setJButtonSignIn("Registrati");
    }

    private void manageEvent() {
        actionListenerWindow();
    }

    private void actionListenerWindow() {
        view.getJDialogMain().addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(view.getJDialogMain(), "Sei sicuro di voler " +
                        "interrompere il login?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.YES_OPTION) {
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


}