package adventure.client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Corona-Extra
 */
public class View {

    private JFrame jframe;
    private JPanel jpanel;
    private JButton buttonEnter;
    private JButton buttonLook;
    private JButton buttonInventory;
    private JButton buttonSouth;
    private JButton buttonEast;
    private JButton buttonWest;
    private JButton buttonNorth;
    private JLabel labelScore;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JTextArea outputArea;
    private JTextArea textAreaScore;
    private JTextField inputField;

    public JFrame getJframe() {
        return jframe;
    }

    public JButton getButtonEnter() {
        return buttonEnter;
    }

    public void setButtonEnter(String nameButton) {
        buttonEnter.setText(nameButton);
    }

    public JButton getButtonLook() {
        return buttonLook;
    }

    public void setButtonLook(String nameButton) {
        buttonLook.setText(nameButton);
    }

    public JButton getButtonInventory() {
        return buttonInventory;
    }

    public void setButtonInventory(String nameButton) {
        buttonInventory.setText(nameButton);
    }

    public JButton getButtonNorth() {
        return buttonNorth;
    }

    public void setButtonNorth(String nameButton) {
        buttonNorth.setText(nameButton);
    }

    public JButton getButtonSouth() {
        return buttonSouth;
    }

    public void setButtonSouth(String nameButton) {
        buttonSouth.setText(nameButton);
    }

    public JButton getButtonEast() {
        return buttonEast;
    }

    public void setButtonEast(String nameButton) {
        buttonEast.setText(nameButton);
    }

    public JButton getButtonWest() {
        return buttonWest;
    }

    public void setButtonWest(String nameButton) {
        buttonWest.setText(nameButton);
    }

    public JTextArea getTextAreaScore() {
        return textAreaScore;
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public void setLabelScore(String nameLabel) {
        labelScore.setText(nameLabel);
    }

    public void setTitle(String title) {
        jframe.setTitle(title);
    }

    public View() {
        initComponents();
        jframe.setIconImage(new ImageIcon("/../../../res/Images/Icon.png").getImage());
    }

    private void initComponents() {

        jframe = new JFrame();
        jpanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        inputField = new JTextField();
        buttonEnter = new JButton();
        jScrollPane2 = new JScrollPane();
        outputArea = new JTextArea();
        buttonLook = new JButton();
        buttonInventory = new JButton();
        jSeparator1 = new JSeparator();
        buttonSouth = new JButton();
        buttonEast = new JButton();
        buttonWest = new JButton();
        buttonNorth = new JButton();
        jSeparator2 = new JSeparator();
        jScrollPane3 = new JScrollPane();
        textAreaScore = new JTextArea();
        labelScore = new JLabel();

        DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        jframe.add(jpanel);
        jpanel.add(jScrollPane1);
        jpanel.add(inputField);
        jpanel.add(buttonEnter);
        jpanel.add(jScrollPane2);
        jpanel.add(outputArea);
        jpanel.add(buttonLook);
        jpanel.add(buttonInventory);
        jpanel.add(jSeparator1);
        jpanel.add(buttonSouth);
        jpanel.add(buttonEast);
        jpanel.add(buttonWest);
        jpanel.add(buttonNorth);
        jpanel.add(jSeparator2);
        jpanel.add(jScrollPane3);
        jpanel.add(textAreaScore);
        jpanel.add(labelScore);

        jframe.add(jpanel);
        jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jframe.setMinimumSize(new Dimension(720, 500));

        ImageIcon imageIcon = new ImageIcon("/../../../res/Images/Icon.png");
        jframe.setIconImage(imageIcon.getImage());

        jScrollPane1.setViewportView(inputField);
        buttonEnter.setText("Invio");

        jScrollPane2.setViewportView(outputArea);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setColumns(20);
        outputArea.setRows(5);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setViewportView(textAreaScore);
        textAreaScore.setEditable(false);
        textAreaScore.setColumns(20);
        textAreaScore.setRows(5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jframe.getContentPane());
        jframe.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane3, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonNorth, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonSouth, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonWest, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonEast, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonInventory, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonLook, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonEnter, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jSeparator2, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jSeparator1, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(labelScore)
                                                .addGap(34, 34, 34))))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelScore)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonNorth, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonSouth, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonEast, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonWest, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonLook, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                                        .addComponent(jScrollPane2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        jframe.pack();
        jframe.setLocationRelativeTo(null); // Used to center the window
    }
}
