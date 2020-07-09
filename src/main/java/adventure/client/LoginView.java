package adventure.client;

import javax.swing.*;
import java.awt.*;

/**
 * @author Corona-Extra
 */
public class LoginView {
    private final JDialog JDialogMain;
    private JLabel JLabelUsername;
    private JTextField JTUsernameField;
    private JLabel JLabelPassword;
    private JPasswordField JPasswordField;
    private JButton JButtonLogin;
    private JButton JButtonSignIn;

    public LoginView(JFrame parentFrame) {
        JDialogMain = new JDialog(parentFrame, true);
        initComponents();
    }

    public JDialog getJDialogMain() {
        return JDialogMain;
    }

    public JLabel getJLabelUsername() {
        return JLabelUsername;
    }

    public void setJLabelUsername(String nameLabel) {
        JLabelUsername.setText(nameLabel);
    }

    public JTextField getJTUsernameField() {
        return JTUsernameField;
    }

    public JLabel getJLabelPassword() {
        return JLabelPassword;
    }

    public void setJLabelPassword(String nameLabel) {
        JLabelPassword.setText(nameLabel);
    }

    public JPasswordField getJPasswordField() {
        return JPasswordField;
    }

    public JButton getJButtonLogin() {
        return JButtonLogin;
    }

    public void setJButtonLogin(String nameButton) {
        JButtonLogin.setText(nameButton);
    }

    public JButton getJButtonSignIn() {
        return JButtonSignIn;
    }

    public void setJButtonSignIn(String nameButton) {
        JButtonSignIn.setText(nameButton);
    }

    private void initComponents() {
        JTUsernameField = new JTextField();
        JLabelUsername = new JLabel();
        JLabelPassword = new JLabel();
        JPasswordField = new JPasswordField();
        JButtonLogin = new JButton();
        JButtonSignIn = new JButton();

        JDialogMain.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JDialogMain.setMinimumSize(new Dimension(520, 300));
        JDialogMain.setResizable(false);
        JDialogMain.getRootPane().setDefaultButton(JButtonLogin);
        JDialogMain.setTitle("Login");
        JDialogMain.add(JLabelUsername);
        JDialogMain.add(JTUsernameField);
        JDialogMain.add(JLabelPassword);
        JDialogMain.add(JPasswordField);
        JDialogMain.add(JButtonLogin);
        JDialogMain.add(JButtonSignIn);

        JButtonLogin.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(JDialogMain.getContentPane());
        JDialogMain.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTUsernameField)
                    .addComponent(JPasswordField)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JLabelUsername)
                            .addComponent(JLabelPassword))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 148, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(JButtonSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JButtonLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(203, 203, 203)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(JLabelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(JLabelPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JButtonLogin)
                .addGap(18, 18, 18)
                .addComponent(JButtonSignIn)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        JDialogMain.pack();
        JDialogMain.setLocationRelativeTo(null);
    }
}
