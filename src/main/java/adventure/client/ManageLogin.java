package adventure.client;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
        this.view.getJDialogMain().setVisible(true);
    }

    public LoginView getView() {
        return view;
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
        keyListenerJTUsernameField();
        keyListenerJPasswordField();
        actionListenerButtonSignUp();
        actionListenerButtonLogin();
    }

    private boolean isValidButtonLogin() {
        boolean validButton = false;
        if(!view.getJTUsernameField().getText().isEmpty() && !String.valueOf(view.getJPasswordField().getPassword()).isEmpty()) {
            validButton = true;
        }

        return validButton;
    }

    private void manageButtonLogin() {
        view.getJButtonLogin().setEnabled(isValidButtonLogin());
    }

    private void actionListenerWindow() {
        view.getJDialogMain().addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(view.getJDialogMain(), "Sei sicuro di voler " +
                        "interrompere il login?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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

    private void keyListenerJTUsernameField() {
        view.getJTUsernameField().addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                manageButtonLogin();
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }

    private void keyListenerJPasswordField() {
        view.getJPasswordField().addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                manageButtonLogin();
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
    }

    private void actionListenerButtonSignUp() {
        view.getJButtonSignIn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageSignUp signUp = new ManageSignUp(in, out, ManageLogin.this);
            }
        });
    }

    private void actionListenerButtonLogin() {
        view.getJButtonLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ciao");
                ManageGameChooser gameChooser = new ManageGameChooser(in, out, gameViewController);
                view.getJDialogMain().setVisible(false);
                view.getJDialogMain().dispose();
            }
        });
    }
}