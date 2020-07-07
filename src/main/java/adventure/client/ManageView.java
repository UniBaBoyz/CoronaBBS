package adventure.client;

import adventure.Utils;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
        manageEvent();
        initView();
        setVisibleWindow(true);
    }

    public void disposeWindow() {
        view.getJframe().setVisible(false);
        view.getJframe().dispose();
    }

    public void setVisibleWindow(boolean value) {
        view.getJframe().setVisible(value);
    }

    public void run() throws IOException {
        // TODO Cambiare condizione
        while (true) {
            manageInput(in.readLine());
        }
    }

    private void manageInput(String string) {
        if (string != null && string.contains(Utils.SEPARATOR_CHARACTER_STRING)) {
            String score = string.substring(string.indexOf(Utils.SEPARATOR_CHARACTER_STRING) + 1, string.length());
            view.getTextAreaScore().setText(score);

            String textToAppend = string.substring(0, string.indexOf(Utils.SEPARATOR_CHARACTER_STRING));
            if(!textToAppend.isEmpty()) {
                view.getOutputArea().append(textToAppend + System.lineSeparator());
            }

        } else if (string != null && !string.isEmpty()) {
            view.getOutputArea().append(string + System.lineSeparator());
        }
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

    private void manageEvent() {
        actionListenerWindow();
        actionListenerButtonNorth();
        actionListenerButtonSouth();
        actionListenerButtonEast();
        actionListenerButtonWest();
        actionListenerButtonInventory();
        actionListenerButtonLook();
        actionListenerButtonEnter();
        actionListenerInputField();
    }

    private void actionListenerWindow() {
        view.getJframe().addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(view.getJframe(), "Sei sicuro di voler uscire dal gioco?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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

    private void actionListenerButtonNorth() {
        view.getButtonNorth().addActionListener(e -> out.println("Nord"));
    }

    private void actionListenerButtonSouth() {
        view.getButtonSouth().addActionListener(e -> out.println("Sud"));
    }

    private void actionListenerButtonEast() {
        view.getButtonEast().addActionListener(e -> out.println("Est"));
    }

    private void actionListenerButtonWest() {
        view.getButtonWest().addActionListener(e -> out.println("Ovest"));
    }

    private void actionListenerButtonInventory() {
        view.getButtonInventory().addActionListener(e -> out.println("Inventario"));
    }

    private void actionListenerButtonLook() {
        view.getButtonLook().addActionListener(e -> out.println("Guarda"));
    }

    private void actionListenerButtonEnter() {
        view.getButtonEnter().addActionListener(ev -> {
            out.println(view.getInputField().getText());
            view.getInputField().setText("");
        });
    }

    private void actionListenerInputField() {
        view.getInputField().addActionListener(ev -> {
            out.println(view.getInputField().getText());
            view.getInputField().setText("");
        });
    }

}
