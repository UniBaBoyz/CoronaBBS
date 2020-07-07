package adventure.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ManageLogin {
    private final BufferedReader in;
    private final PrintWriter out;
    private final LoginView view;

    public ManageLogin(BufferedReader in, PrintWriter out, GameView game) {
        this.in = in;
        this.out = out;
        view = new LoginView(game.getJFmainFrame(), true);
        view.setVisible(true);
        /*manageEvent();
        initView();*/
    }

    public void run() throws IOException {
        // TODO Cambiare condizione
        while (true) {
            manageInput(in.readLine());
        }
    }

    private void manageInput(String string) {

    }

    /*private void initView() {
        view.setButtonLook("Guarda");
        view.setButtonInventory("Inventario");
        view.setButtonEnter("Invio");
        view.setButtonNorth("Nord");
        view.setButtonSouth("Sud");
        view.setButtonEast("Est");
        view.setButtonWest("Ovest");
        view.setLabelScore("Score");
    }*/

    /*private void manageEvent() {
        actionListenerWindow();
        actionListenerButtonNorth();
        actionListenerButtonSouth();
        actionListenerButtonEast();
        actionListenerButtonWest();
        actionListenerButtonInventory();
        actionListenerButtonLook();
        actionListenerButtonEnter();
        actionListenerInputField();
    }*/

}