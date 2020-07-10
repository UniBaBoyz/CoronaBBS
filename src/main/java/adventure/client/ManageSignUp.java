package adventure.client;

import adventure.utils.Utils;

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
        view.setJButtonSignUp("Registrati");
        view.getJButtonSignUp().setToolTipText("Clicca qui per registrarti");
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
            String response = in.readLine();
            if (response != null) {
                switch (response) {
                    case INVALID_PASSWORD:
                        JOptionPane.showMessageDialog(view, "La Password non è valida,\n" +
                                        "La password deve rispettare le seguenti proprietà:\n" +
                                        "- Contenere almeno un carattere maiuscolo\n" +
                                        "- Contenere almeno un carattere maiuscolo\n" +
                                        "- Contenere almeno un numero\n" +
                                        "- Deve essere lunga minimo 6 caratteri e massimo 40",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case EXISTING_USERNAME:
                        JOptionPane.showMessageDialog(view, "Il nome utente è stato già preso",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case CORRECT_REGISTRATION:
                        JOptionPane.showMessageDialog(view, "L'iscrizione è avvenuta con successo!",
                                "Registrazione", JOptionPane.INFORMATION_MESSAGE);
                        disposeWindow();
                        break;
                }
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
                SEPARATOR_CHARACTER_DATE +
                comboMonth.getItemAt(comboMonth.getSelectedIndex()).toString() +
                SEPARATOR_CHARACTER_DATE +
                comboDay.getItemAt(comboDay.getSelectedIndex()).toString();
    }

    private String createResponse() {
        return view.getJTUsernameField().getText() +
                SEPARATOR_CHARACTER_STRING +
                String.valueOf(view.getJPasswordField().getPassword()) +
                SEPARATOR_CHARACTER_STRING +
                manageDate(view.getJComboBoxDay(), view.getJComboBoxMonth(), view.getJComboBoxYear()) +
                SEPARATOR_CHARACTER_STRING +
                view.getJTResidenceField().getText();
    }

    private void resetView() {
        view.getJTUsernameField().setText("");
        view.getJPasswordField().setText("");
        view.getJComboBoxDay().setSelectedIndex(0);
        view.getJComboBoxMonth().setSelectedIndex(0);
        view.getJComboBoxYear().setSelectedIndex(0);
        view.getJTResidenceField().setText("");
        view.getJButtonSignUp().setEnabled(false);
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

    private void actionListenerButtonSignUp() {
        view.getJButtonSignUp().addActionListener(e -> {
            if (!Utils.isValidDate(
                    Integer.parseInt(view.getJComboBoxDay().getItemAt(view.getJComboBoxDay().getSelectedIndex())),
                    Integer.parseInt(view.getJComboBoxMonth().getItemAt(view.getJComboBoxMonth().getSelectedIndex())),
                    Integer.parseInt(view.getJComboBoxYear().getItemAt(view.getJComboBoxYear().getSelectedIndex())))) {
                JOptionPane.showMessageDialog(view, "La data non è valida", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (!view.getJTUsernameField().getText().matches(USERNAME_REGEX)) {
                JOptionPane.showMessageDialog(view, "Il nome utente non è valido\n" +
                                "Il nome utente deve contenere minimo 4 caratteri e massimo 30",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (!view.getJTResidenceField().getText().matches(RESIDENCE_REGEX)) {
                JOptionPane.showMessageDialog(view, "La residenza non è valida\n" +
                        "La residenza deve contenere minimo 2 e massimo 50 e non può contenere numeri",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                out.println(REGISTRATION);
                out.println(createResponse());
                run();
                resetView();
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
