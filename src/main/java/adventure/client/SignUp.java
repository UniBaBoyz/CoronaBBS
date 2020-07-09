/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure.client;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mstel
 */
public class SignUp extends javax.swing.JFrame {

    private final JDialog JDialogMain;
    private JLabel JLabelUsername;
    private JTextField JTUsernameField;
    private JLabel JLabelPassword;
    private JPasswordField JPasswordField;
    private JLabel JLabelDate;
    private JLabel JLabelDay;
    private JComboBox<String> JComboBoxDay;
    private JLabel JLabelMonth;
    private JComboBox<String> JComboBoxMonth;
    private JLabel JLabelYear;
    private JComboBox<String> JComboBoxYear;
    private JLabel JLabelResidence;
    private JTextField JTResidenceField;
    private JButton JButtonSignUp;

    public SignUp(JDialog parentDialog) {
        JDialogMain = new JDialog(parentDialog, true);
        initComponents();
    }

    public JDialog getJDialogMain() {
        return JDialogMain;
    }

    public JLabel getJLabelUsername() {
        return JLabelUsername;
    }

    public void setJLabelUsername(String JLabelUsername) {
        this.JLabelUsername.setText(JLabelUsername);
    }

    public JTextField getJTUsernameField() {
        return JTUsernameField;
    }

    public JLabel getJLabelPassword() {
        return JLabelPassword;
    }

    public void setJLabelPassword(String JLabelPassword) {
        this.JLabelPassword.setText(JLabelPassword);
    }

    public JPasswordField getJPasswordField() {
        return JPasswordField;
    }

    public JLabel getJLabelDate() {
        return JLabelDate;
    }

    public JLabel getJLabelDay() {
        return JLabelDay;
    }

    public void setJLabelDate(String JLabelDate) {
        this.JLabelDate.setText(JLabelDate);
    }

    public JComboBox<String> getJComboBoxDay() {
        return JComboBoxDay;
    }

    public void setJLabelDay(String JLabelDay) {
        this.JLabelDay.setText(JLabelDay);
    }

    public JLabel getJLabelMonth() {
        return JLabelMonth;
    }

    public JComboBox<String> getJComboBoxMonth() {
        return JComboBoxMonth;
    }

    public void setJLabelMonth(String JLabelMonth) {
        this.JLabelMonth.setText(JLabelMonth);
    }

    public JLabel getJLabelYear() {
        return JLabelYear;
    }

    public JComboBox<String> getJComboBoxYear() {
        return JComboBoxYear;
    }

    public void setJLabelYear(String JLabelYear) {
        this.JLabelYear.setText(JLabelYear);
    }

    public JLabel getJLabelResidence() {
        return JLabelResidence;
    }

    public JTextField getJTResidenceField() {
        return JTResidenceField;
    }

    public void setJLabelResidence(String JLabelResidence) {
        this.JLabelResidence.setText(JLabelResidence);
    }

    public JButton getJButtonSignUp() {
        return JButtonSignUp;
    }

    public void setJButtonSignUp(String JButtonSignUp) {
        this.JButtonSignUp.setText(JButtonSignUp);
    }

    private void initComponents() {

        JTUsernameField = new JTextField();
        JLabelUsername = new JLabel();
        JLabelPassword = new JLabel();
        JPasswordField = new JPasswordField();
        JButtonSignUp = new JButton();
        JLabelDate = new JLabel();
        JLabelResidence = new JLabel();
        JTResidenceField = new JTextField();
        JComboBoxDay = new JComboBox<>();
        JLabelDay = new JLabel();
        JLabelMonth = new JLabel();
        JComboBoxMonth = new JComboBox<>();
        JLabelYear = new JLabel();
        JComboBoxYear = new JComboBox<>();

        JDialogMain.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JDialogMain.getRootPane().setDefaultButton(JButtonSignUp);
        JDialogMain.setTitle("Registrazione");
        JDialogMain.add(JButtonSignUp);
        JDialogMain.add(JComboBoxDay);
        JDialogMain.add(JComboBoxMonth);
        JDialogMain.add(JComboBoxYear);
        JDialogMain.add(JLabelUsername);
        JDialogMain.add(JLabelPassword);
        JDialogMain.add(JLabelDate);
        JDialogMain.add(JLabelResidence);
        JDialogMain.add(JLabelDay);
        JDialogMain.add(JLabelMonth);
        JDialogMain.add(JLabelYear);
        JDialogMain.add(JPasswordField);
        JDialogMain.add(JTUsernameField);
        JDialogMain.add(JTResidenceField);

        JButtonSignUp.setEnabled(false);
        JDialogMain.setResizable(false);
        JDialogMain.setMinimumSize(new Dimension(520, 350));
        JTUsernameField.setMaximumSize(new Dimension(480,10));
        JTResidenceField.setMaximumSize(new Dimension(480,10));
        JPasswordField.setMaximumSize(new Dimension(480,10));

        JComboBoxDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31" }));

        JComboBoxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05",
                "06", "07", "08", "09", "10", "11", "12" }));

        JComboBoxYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1950", "1951", "1952", "1953",
                "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965",
                "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977",
                "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989",
                "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001",
                "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014" }));


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(JDialogMain.getContentPane());
        JDialogMain.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(JButtonSignUp))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(JLabelDate)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JTResidenceField, GroupLayout.Alignment.LEADING)
                            .addComponent(JTUsernameField)
                            .addComponent(JPasswordField)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JLabelUsername)
                                    .addComponent(JLabelPassword)
                                    .addComponent(JLabelResidence)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(JLabelDay)
                                        .addGap(13, 13, 13)
                                        .addComponent(JComboBoxDay, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(JLabelMonth)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(JComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(JLabelYear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JComboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(JLabelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JLabelPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JLabelDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(JComboBoxDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLabelDay)
                    .addComponent(JLabelMonth)
                    .addComponent(JComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLabelYear)
                    .addComponent(JComboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(JLabelResidence)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTResidenceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(JButtonSignUp)
                .addContainerGap())
        );
        JDialogMain.pack();
        JDialogMain.setLocationRelativeTo(null);
    }


}
