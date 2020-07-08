package adventure.client;

import adventure.Utils;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ManageSignUp {
    private final BufferedReader in;
    private final PrintWriter out;
    private final SignUp view;
    private ManageLogin loginViewController;

    public ManageSignUp(BufferedReader in, PrintWriter out, ManageLogin loginViewController) {
        this.in = in;
        this.out = out;
        this.loginViewController = loginViewController;
        view = new SignUp(this.loginViewController.getView().getJDialogMain());
        manageEvent();
        initView();
        view.getJDialogMain().setVisible(true);
        /*run();
        disposeWindow();*/
    }

    private void initView() {
        view.setJLabelUsername("Username");
        view.setJLabelPassword("Password");
        view.setJButtonSignUp("Registrati");
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

    private void run() {
        try {
            while (in.readLine().matches(Utils.CORRECT_REGISTRATION)) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manageEvent() {
        actionListenerWindow();
        keyListenerJTUsernameField();
        keyListenerJTResidenceField();
        keyListenerJPasswordField();
        actionListenerButtonSignUp();
    }

    private String manageDate(JComboBox comboDay, JComboBox comboMonth, JComboBox comboYear) {
        return comboYear.getItemAt(comboYear.getSelectedIndex()).toString() +
                Utils.SEPARATOR_CHARACTER_DATE +
                comboMonth.getItemAt(comboMonth.getSelectedIndex()).toString() +
                Utils.SEPARATOR_CHARACTER_DATE +
                comboDay.getItemAt(comboDay.getSelectedIndex()).toString();
    }

    private String createResponse() {
        return view.getJTUsernameField().getText() +
                Utils.SEPARATOR_CHARACTER_STRING +
                String.valueOf(view.getJPasswordField().getPassword()) +
                Utils.SEPARATOR_CHARACTER_STRING +
                manageDate(view.getJComboBoxDay(), view.getJComboBoxMonth(), view.getJComboBoxYear()) +
                Utils.SEPARATOR_CHARACTER_STRING +
                view.getJTResidenceField().getText();
    }

    private void actionListenerWindow() {
        view.getJDialogMain().addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(loginViewController.getView().getJDialogMain(), "Sei sicuro di voler " +
                        "interrompere la registrazione?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == JOptionPane.YES_OPTION) {
                    disposeWindow();
                    ManageLogin login = new ManageLogin(in, out, loginViewController.getGameViewController());
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

    private void actionListenerButtonSignUp() {
        view.getJButtonSignUp().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Utils.isValidDate(
                        Integer.parseInt(view.getJComboBoxDay().getItemAt(view.getJComboBoxDay().getSelectedIndex())),
                        Integer.parseInt(view.getJComboBoxMonth().getItemAt(view.getJComboBoxMonth().getSelectedIndex())),
                        Integer.parseInt(view.getJComboBoxYear().getItemAt(view.getJComboBoxYear().getSelectedIndex())))) {
                    JOptionPane.showMessageDialog(view, "La data non è valida", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    out.println(Utils.REGISTRATION);
                    out.println(createResponse());

                }
            }
        });
    }

    private boolean isValidButtonSignUp() {
        boolean validButton = false;
        char[] password = view.getJPasswordField().getPassword();
        if (!view.getJTResidenceField().getText().isEmpty()
                && !view.getJTUsernameField().getText().isEmpty()
                && !String.valueOf(password).isEmpty()) {
            validButton = true;
        }

        return validButton;
    }

    private void manageButtonSignUp() {
        view.getJButtonSignUp().setEnabled(isValidButtonSignUp());
    }

    private void keyListenerJTUsernameField() {
        view.getJTUsernameField().addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                manageButtonSignUp();
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }

    private void keyListenerJTResidenceField() {
        view.getJTResidenceField().addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                manageButtonSignUp();
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
                manageButtonSignUp();
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
    }

}
