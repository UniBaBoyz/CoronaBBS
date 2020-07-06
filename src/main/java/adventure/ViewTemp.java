package adventure;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ViewTemp {

    private final JFrame JFmainFrame = new JFrame("Client");
    private final JPanel JPmainPanel = new JPanel();

    private final JPanel JPNorthPanel = new JPanel();
    private final JTextArea JTmainOutputArea = new JTextArea();
    private final JScrollPane JSoutputAreaScroller = new JScrollPane(JTmainOutputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private final DefaultCaret caret = (DefaultCaret) JTmainOutputArea.getCaret();

    private final JPanel JPButtonPanel = new JPanel();
    private final JButton JButtonNorth = new JButton("Nord");
    private final JButton JButtonSouth = new JButton("Sud");
    private final JButton JBttonEast = new JButton("Est");
    private final JButton JButtonWest = new JButton("Ovest");
    private final JButton JButtonEnter = new JButton("Invio");
    private final JButton JButtonLook = new JButton("Guarda");
    private final JButton JButtonInventory = new JButton("Inventario");

    private final JPanel JPSouthPanel = new JPanel();
    private final JLabel JLinputLabel = new JLabel("Input: ");
    private final JTextField JTmainInputField = new JTextField();


    public ViewTemp() {
        // JFrame options
        JFmainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Used to check windowListener
        JFmainFrame.setFocusable(false);
        JFmainFrame.setMinimumSize(new Dimension(750, 500));
        JFmainFrame.setSize(750, 500);
        JFmainFrame.setLocationRelativeTo(null); // Used to center the window
        JFmainFrame.getContentPane().add(JPmainPanel);
        JFmainFrame.getRootPane().setDefaultButton(JButtonEnter); // Used to action the button when the user press enter
        JFmainFrame.addWindowListener(new WindowsListener());

        // Main JPanel options
        JPmainPanel.setLayout(new BoxLayout(JPmainPanel, BoxLayout.PAGE_AXIS));
        JPmainPanel.add(Box.createRigidArea(new Dimension(750, 10))); // Used to add spaces between component
        JPmainPanel.add(JPNorthPanel);
        JPmainPanel.add(Box.createRigidArea(new Dimension(750, 10))); // Used to add spaces between component
        JPmainPanel.add(JPSouthPanel);
        JPmainPanel.setBackground(Color.decode("#A0BAB5"));

        //JPNorthPanel options
        JPNorthPanel.setLayout(new BoxLayout(JPNorthPanel, BoxLayout.LINE_AXIS));
        JPNorthPanel.add(Box.createRigidArea(new Dimension(10, 400)));
        JPNorthPanel.add(JSoutputAreaScroller);
        JPNorthPanel.setMinimumSize(new Dimension(720, 400));
        JPNorthPanel.add(Box.createRigidArea(new Dimension(10, 400)));
        JPNorthPanel.add(JPButtonPanel);
        JPNorthPanel.add(Box.createRigidArea(new Dimension(10, 400)));
        JPNorthPanel.setBackground(Color.magenta);

        // JScrollPane options
        JSoutputAreaScroller.setMinimumSize(new Dimension(720, 400));
        JSoutputAreaScroller.setAutoscrolls(true);
        JSoutputAreaScroller.setForeground(Color.BLACK);

        // Caret management
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // JTextArea options
        JTmainOutputArea.setRows(10);
        JTmainOutputArea.setColumns(10);
        JTmainOutputArea.setEditable(false);
        JTmainOutputArea.setLineWrap(true);
        JTmainOutputArea.setWrapStyleWord(true);
        JTmainOutputArea.setAutoscrolls(true);
        JTmainOutputArea.setForeground(Color.BLACK);
        JTmainOutputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTmainOutputArea.setToolTipText("Inserisci il tuo input qui");

        // Input JPanel options
        JPSouthPanel.add(JLinputLabel);
        JPSouthPanel.add(JTmainInputField);
        JPSouthPanel.add(Box.createRigidArea(new Dimension(20, 50)));
        JPSouthPanel.add(JButtonEnter);
        JPSouthPanel.setMinimumSize(new Dimension(750, 50));
        JPSouthPanel.setMaximumSize(new Dimension(750, 50));
        JPSouthPanel.setBackground(Color.decode("#A0BAB5"));
        JPSouthPanel.setLayout(new FlowLayout());

        // JLabel options
        JLinputLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // JTextField options
        JTmainInputField.setEditable(true);
        JTmainInputField.setAutoscrolls(true);
        JTmainInputField.setEnabled(true);
        JTmainInputField.setColumns(50);
        JTmainInputField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setWindowVisible() {
        JFmainFrame.setVisible(true);
    }

    public void disposeWindow() {
        JFmainFrame.setVisible(false);
        JFmainFrame.dispose();
    }

    // Action listeners
    private class WindowsListener implements WindowListener {

        @Override
        public void windowClosing(WindowEvent e) {
            int input = JOptionPane.showConfirmDialog(JFmainFrame, "Sei sicuro di voler uscire dal gioco?", "Esci", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == JOptionPane.YES_OPTION) {
                disposeWindow();
            }
        }

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

}
