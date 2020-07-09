/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure.client;

import javax.swing.*;
import java.awt.*;

/**
 * @author Corona-Extra
 */
public class GameChooserView extends javax.swing.JFrame {

    private final JDialog JDialogMain;
    private JButton JButtonLoadGame;
    private JButton JButtonNewGame;
    private JLabel JLabelSelectionGame;
    private JComboBox<String> JComboBox1;

    public JDialog getJDialogMain() {
        return JDialogMain;
    }

    public JButton getJButtonLoadGame() {
        return JButtonLoadGame;
    }

    public JComboBox<String> getJComboBox1() {
        return JComboBox1;
    }

    public JButton getJButtonNewGame() {
        return JButtonNewGame;
    }

    public JLabel getJLabelSelectionGame() {
        return JLabelSelectionGame;
    }

    public void setJButtonLoadGame(String JButtonLoadGame) {
        this.JButtonLoadGame.setName(JButtonLoadGame);
    }

    public void setJButtonNewGame(String JButtonNewGame) {
        this.JButtonNewGame.setName(JButtonNewGame);
    }

    public void setJLabelSelectionGame(String JLabelSelectionGame) {
        this.JLabelSelectionGame.setName(JLabelSelectionGame);
    }

    public GameChooserView(JFrame parentFrame) {
        JDialogMain = new JDialog(parentFrame, true);
        initComponents();
    }

    private void initComponents() {

        JLabelSelectionGame = new JLabel();
        JComboBox1 = new JComboBox<>();
        JButtonLoadGame = new JButton();
        JButtonNewGame = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JDialogMain.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JDialogMain.setMinimumSize(new Dimension(520, 300));
        JDialogMain.setResizable(false);
        JDialogMain.setTitle("Giochi disponibili");
        JDialogMain.add(JComboBox1);
        JDialogMain.add(JButtonLoadGame);
        JDialogMain.add(JLabelSelectionGame);
        JDialogMain.add(JButtonNewGame);

        JLabelSelectionGame.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 12)); // NOI18N
        JLabelSelectionGame.setText("Seleziona il gioco:");

        JComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PrisonBreak", "FireHouse" }));

        JButtonLoadGame.setText("Carica Partita");

        JButtonNewGame.setText("Nuova Partita");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(JDialogMain.getContentPane());
        JDialogMain.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JLabelSelectionGame)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JButtonNewGame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                        .addComponent(JButtonLoadGame)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(JLabelSelectionGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JButtonLoadGame)
                    .addComponent(JButtonNewGame))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        JDialogMain.pack();
        JDialogMain.setLocationRelativeTo(null);
    }
}
