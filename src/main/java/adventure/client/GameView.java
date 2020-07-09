package adventure.client;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * @author Corona-Extra
 */
public class GameView {

    private JFrame JFmainFrame;
    private JPanel JPmainPanel;
    private JButton JButtonEnter;
    private JButton JButtonLook;
    private JButton JButtonInventory;
    private JButton JButtonSouth;
    private JButton JButtonEast;
    private JButton JButtonWest;
    private JButton JButtonNorth;
    private JLabel JLabelScore;
    private JScrollPane JScrollPane1;
    private JScrollPane JScrollPane2;
    private JScrollPane JScrollPane3;
    private JSeparator JSeparator1;
    private JSeparator JSeparator2;
    private JTextArea JToutputArea;
    private JTextArea JTextAreaScore;
    private JTextField JTinputField;

    public JFrame getJFmainFrame() {
        return JFmainFrame;
    }

    public JButton getJButtonEnter() {
        return JButtonEnter;
    }

    public void setJButtonEnter(String nameButton) {
        JButtonEnter.setText(nameButton);
    }

    public JButton getJButtonLook() {
        return JButtonLook;
    }

    public void setJButtonLook(String nameButton) {
        JButtonLook.setText(nameButton);
    }

    public JButton getJButtonInventory() {
        return JButtonInventory;
    }

    public void setJButtonInventory(String nameButton) {
        JButtonInventory.setText(nameButton);
    }

    public JButton getJButtonNorth() {
        return JButtonNorth;
    }

    public void setJButtonNorth(String nameButton) {
        JButtonNorth.setText(nameButton);
    }

    public JButton getJButtonSouth() {
        return JButtonSouth;
    }

    public void setJButtonSouth(String nameButton) {
        JButtonSouth.setText(nameButton);
    }

    public JButton getJButtonEast() {
        return JButtonEast;
    }

    public void setJButtonEast(String nameButton) {
        JButtonEast.setText(nameButton);
    }

    public JButton getJButtonWest() {
        return JButtonWest;
    }

    public void setJButtonWest(String nameButton) {
        JButtonWest.setText(nameButton);
    }

    public JTextArea getJTextAreaScore() {
        return JTextAreaScore;
    }

    public JTextArea getJToutputArea() {
        return JToutputArea;
    }

    public JTextField getJTinputField() {
        return JTinputField;
    }

    public void setJLabelScore(String nameLabel) {
        JLabelScore.setText(nameLabel);
    }

    public void setTitle(String title) {
        JFmainFrame.setTitle(title);
    }

    public GameView() {
        initComponents();
    }

    private void initComponents() {

        JFmainFrame = new JFrame();
        JPmainPanel = new JPanel();
        JScrollPane1 = new JScrollPane();
        JTinputField = new JTextField();
        JButtonEnter = new JButton();
        JScrollPane2 = new JScrollPane();
        JToutputArea = new JTextArea();
        JButtonLook = new JButton();
        JButtonInventory = new JButton();
        JSeparator1 = new JSeparator();
        JButtonSouth = new JButton();
        JButtonEast = new JButton();
        JButtonWest = new JButton();
        JButtonNorth = new JButton();
        JSeparator2 = new JSeparator();
        JScrollPane3 = new JScrollPane();
        JTextAreaScore = new JTextArea();
        JLabelScore = new JLabel();

        DefaultCaret caret = (DefaultCaret) JToutputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JFmainFrame.add(JPmainPanel);
        JPmainPanel.add(JScrollPane1);
        JPmainPanel.add(JTinputField);
        JPmainPanel.add(JButtonEnter);
        JPmainPanel.add(JScrollPane2);
        JPmainPanel.add(JToutputArea);
        JPmainPanel.add(JButtonLook);
        JPmainPanel.add(JButtonInventory);
        JPmainPanel.add(JSeparator1);
        JPmainPanel.add(JButtonSouth);
        JPmainPanel.add(JButtonEast);
        JPmainPanel.add(JButtonWest);
        JPmainPanel.add(JButtonNorth);
        JPmainPanel.add(JSeparator2);
        JPmainPanel.add(JScrollPane3);
        JPmainPanel.add(JTextAreaScore);
        JPmainPanel.add(JLabelScore);

        JFmainFrame.add(JPmainPanel);
        JFmainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JFmainFrame.setMinimumSize(new Dimension(720, 500));

        ImageIcon imageIcon = new ImageIcon("/../../../res/Images/Icon.png");
        JFmainFrame.setIconImage(imageIcon.getImage());

        JScrollPane1.setViewportView(JTinputField);
        JButtonEnter.setText("Invio");

        JScrollPane2.setViewportView(JToutputArea);
        JToutputArea.setEditable(false);
        JToutputArea.setLineWrap(true);
        JToutputArea.setWrapStyleWord(true);
        JToutputArea.setColumns(20);
        JToutputArea.setRows(5);

        JScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        JScrollPane3.setViewportView(JTextAreaScore);
        JTextAreaScore.setEditable(false);
        JTextAreaScore.setColumns(20);
        JTextAreaScore.setRows(5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(JFmainFrame.getContentPane());
        JFmainFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(JScrollPane1)
                                        .addComponent(JScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(JScrollPane3, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JButtonNorth, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JButtonSouth, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JButtonWest, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JButtonEast, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JButtonInventory, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JButtonLook, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JButtonEnter, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JSeparator2, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(JSeparator1, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(JLabelScore)
                                                .addGap(34, 34, 34))))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(JLabelScore)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JButtonNorth, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JButtonSouth, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JButtonEast, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JButtonWest, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JButtonInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JButtonLook, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                                        .addComponent(JScrollPane2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(JScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(JButtonEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        JFmainFrame.pack();
        JFmainFrame.setLocationRelativeTo(null); // Used to center the window
    }
}
