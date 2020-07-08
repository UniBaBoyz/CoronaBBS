package adventure.client;

import javax.swing.*;

/**
 * @author Corona-Extra
 */
public class LoginView extends javax.swing.JDialog {

    private JFrame jframe;
    private JPanel jpanel;
    private JLabel labelUsername;
    private JTextField inputFieldUsername;
    private JLabel labelPassword;
    private JPasswordField inputFieldPassword;
    private JButton buttonEnter;
    private JButton signUpButton;

    public LoginView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        JFrame jframe = new JFrame("Input");
        JPanel jpanel = new JPanel();
        JLabel labelUsername = new JLabel("Username");
        JTextField inputFieldUsername = new JTextField();
        JLabel labelPassword = new JLabel("Password");
        JPasswordField inputFieldPassword = new JPasswordField();
        JButton buttonEnter = new JButton();
        JButton signUpButton = new JButton();

        jframe.getRootPane().add(jpanel);
        jpanel.add(labelUsername);
        jpanel.add(inputFieldUsername);
        jpanel.add(labelPassword);
        jpanel.add(inputFieldPassword);
        jpanel.add(buttonEnter);
        jpanel.add(signUpButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }
}
