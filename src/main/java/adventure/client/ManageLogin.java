package adventure.client;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static adventure.utils.Utils.*;

/**
 * @author Corona-Extra
 */
public class ManageLogin {
    private final BufferedReader in;
    private final PrintWriter out;
    private final LoginView view;
    private final ManageGameView gameViewController;

    public ManageLogin(BufferedReader in, PrintWriter out, ManageGameView gameViewController) {
        this.in = in;
        this.out = out;
        this.gameViewController = gameViewController;
        view = new LoginView(this.gameViewController.getView().getJFmainFrame());
        manageEvent();
        initView();
        this.view.getJDialogMain().setVisible(true);
    }

    public ManageGameView getGameViewController() {
        return gameViewController;
    }

    public LoginView getView() {
        return view;
    }

    public void disposeWindow() {
        view.getJDialogMain().setVisible(false);
        view.getJDialogMain().dispose();
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
        if (!view.getJTUsernameField().getText().isEmpty() && !String.valueOf(view.getJPasswordField().getPassword()).isEmpty()) {
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
        view.getJButtonSignIn().addActionListener(e -> new ManageSignUp(in, out, ManageLogin.this));
    }

    private void resetView() {
        view.getJTUsernameField().setText("");
        view.getJPasswordField().setText("");
        view.getJButtonLogin().setEnabled(false);
    }

    private void run() {
        try {
            String response = in.readLine();
            if (response != null) {
                switch (response) {
                    case NON_EXISTING_USER:
                        JOptionPane.showMessageDialog(view.getJDialogMain(), "Nessun utente trovato!",
                                "ERROR!", JOptionPane.ERROR_MESSAGE);
                        resetView();
                        break;
                    case INVALID_PASSWORD:
                        JOptionPane.showMessageDialog(view.getJDialogMain(), "Password non corretta!",
                                "ERROR!", JOptionPane.ERROR_MESSAGE);
                        resetView();
                        break;
                    case CORRECT_LOGIN:
                        JOptionPane.showMessageDialog(view.getJDialogMain(), "Benvenuto!",
                                "Utente trovato", JOptionPane.INFORMATION_MESSAGE);
                        new ManageGameChooser(in, out, this);
                        break;
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void actionListenerButtonLogin() {
        view.getJButtonLogin().addActionListener(e -> {
            out.println(LOGIN);
            out.println(view.getJTUsernameField().getText() +
                    SEPARATOR_CHARACTER_STRING +
                    String.valueOf(view.getJPasswordField().getPassword()));
            run();
        });
    }
}