package adventure.client;

import javax.swing.*;
import java.awt.*;

public class LoginTemp {
    private JDialog JFmainFrame;
    private JPanel JPmainPanel = new JPanel();
    private JPanel JPusernamePanel = new JPanel();
    private JLabel JLabelUsername = new JLabel("Username");
    private JTextField JTinputdUsername = new JTextField();
    private JPanel JPasswordPanel = new JPanel();
    private JLabel JLabelPassword = new JLabel("Password");
    private JPasswordField JTinputPassword = new JPasswordField();
    private JPanel JPbuttonPanel = new JPanel();
    private JButton JButtonLogin = new JButton("Login");
    private JButton JButtonSignIn = new JButton("Registrati");


    public LoginTemp(JFrame parentFrame) {
        JFmainFrame = new JDialog(parentFrame, "Input", true);
        JFmainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Used to check windowListener
        JFmainFrame.setMinimumSize(new Dimension(550, 300));
        JFmainFrame.setLocationRelativeTo(null); // Used to center the window
        JFmainFrame.getContentPane().add(JPmainPanel);

        // Main JPanel options
        JPmainPanel.setLayout(new BoxLayout(JPmainPanel, BoxLayout.PAGE_AXIS));
        JPmainPanel.add(Box.createRigidArea(new Dimension(JFmainFrame.getWidth(), 50))); // Used to add spaces between component
        JPmainPanel.add(JPusernamePanel);
        JPmainPanel.add(Box.createRigidArea(new Dimension(JFmainFrame.getWidth(), 10))); // Used to add spaces between component
        JPmainPanel.add(JPasswordPanel);
        JPmainPanel.add(Box.createRigidArea(new Dimension(JFmainFrame.getWidth(), 10))); // Used to add spaces between component
        JPmainPanel.add(JPbuttonPanel);

        JPusernamePanel.setLayout(new BoxLayout(JPusernamePanel, BoxLayout.LINE_AXIS));
        JPusernamePanel.add(JLabelUsername);
        JPusernamePanel.add(JTinputdUsername);

        JTinputdUsername.setEditable(true);
        JTinputdUsername.setAutoscrolls(true);
        JTinputdUsername.setEnabled(true);
        //JTinputdUsername.

        JPasswordPanel.setLayout(new BoxLayout(JPasswordPanel, BoxLayout.LINE_AXIS));
        JPasswordPanel.add(JLabelPassword);
        JPasswordPanel.add(JTinputPassword);

        JTinputPassword.setEditable(true);
        JTinputPassword.setAutoscrolls(true);
        JTinputPassword.setEnabled(true);

        JPbuttonPanel.setLayout(new BoxLayout(JPbuttonPanel, BoxLayout.LINE_AXIS));
        JPbuttonPanel.add(JButtonLogin);
        JPbuttonPanel.add(JButtonSignIn);

        JFmainFrame.setVisible(true);
    }
}
