package adventure.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ManageSignUp {
    private final BufferedReader in;
    private final PrintWriter out;
    private final SignUp view;
    private final ManageLogin loginViewController;

    public ManageSignUp(BufferedReader in, PrintWriter out, ManageLogin loginViewController) {
        this.in = in;
        this.out = out;
        this.loginViewController = loginViewController;
        view = new SignUp(this.loginViewController.getView().getJDialogMain());
        manageEvent();
        initView();
        view.getJDialogMain().setVisible(true);
    }

    private void initView() {
        view.setJLabelUsername("Username");
        view.setJLabelPassword("Password");
        view.setJButtonSignUp("Registrati:");
        view.setJLabelDate("Data di nascita:");
        view.setJLabelDay("Giorno:");
        view.setJLabelMonth("Mese:");
        view.setJLabelResidence("Residenza:");
        view.setJLabelYear("Anno:");
    }

    public void disposeWindow() {
        view.getJDialogMain().setVisible(false);
        view.getJDialogMain().dispose();
    }

    private void manageEvent() {
        actionListenerWindow();
    }

    private void actionListenerWindow() {
        view.getJDialogMain().addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(loginViewController.getView().getJDialogMain(), "Sei sicuro di voler " +
                        "interrompere la registrazione?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.YES_OPTION) {
                    disposeWindow();
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
