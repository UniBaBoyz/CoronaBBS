package adventure.client;

import adventure.utils.Utils;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

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
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                        StandardCharsets.UTF_8)),
                        true);
            } else {
                throw new IOException();
            }

            ManageGameView view = new ManageGameView(in, out);
            new ManageLogin(in, out, view);

            while (view.getView().getJFmainFrame().isVisible()) {
                view.run();
            }

            out.println(Utils.EXIT);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Non è possibile comunicare con il server!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Non è possibile chiudere la connessione " +
                        "con il server!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

