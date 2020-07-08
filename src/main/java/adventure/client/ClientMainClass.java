package adventure.client;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Corona-Extra
 */
public class ClientMainClass {
    public static void main(String[] args) {
        Socket socket = null;
        final String addressServer = "localhost";
        final int port = 10000;

        try {
            InetAddress address = InetAddress.getByName(addressServer);
            socket = new Socket(address, port);
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'" +
                    "ottenimento dell'indirizzo IP del server", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Si è verificato un errore durante " +
                    "la connessione con il server", "Errore", JOptionPane.ERROR_MESSAGE);
        }

        try {
            BufferedReader in;
            PrintWriter out;

            if (socket != null) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } else {
                throw new IOException();
            }

            ManageGameView view = new ManageGameView(in, out);
            //ManageLogin login = new ManageLogin(in, out, view.getView());
            LoginView l = new LoginView(view.getView().getJFmainFrame());

            /*if (!view.setUsername()) {
                view.setWindowVisible();
                while (!view.isConnectionClosed()) {
                    view.manageInput();
                }
            }*/

            //TODO AGGIUNGERE LOGIN E COMUNICARLO AL SERVER

            //TODO AGGIUNGERE SCELTA GIOCO E COMUNICARLO AL SERVER


            view.run();


            //view.disposeWindow();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Non e' possibile comunicare con il server!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Non e' possibile chiudere la connessione " +
                        "con il server!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

