package ua.samosfator.gmmtools;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPicker {
    private JPanel MainWindow;
    private JButton rectangleButton;
    private JButton circleButton;
    private JPanel aboutPanel;
    private JTabbedPane tabbedPane;
    private JTextPane howto;
    private JTextPane aboutTextPane;
    private JPanel selectShapePane;

    public ActionPicker() {
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window mainWindow = SwingUtilities.windowForComponent((Component) e.getSource());
                mainWindow.setVisible(false);
                try {
                    RectSizeSetter rss = new RectSizeSetter();
                } catch (Exception ignored) {}
            }
        });
        rectangleButton.setIcon(new ImageIcon("img\\rectangle.png"));
        circleButton.setIcon(new ImageIcon("img\\circle.png"));
        circleButton.setEnabled(false);

        howto.setBackground(UIManager.getColor("TabbedPane.background"));
        howto.setHighlighter(null);

        aboutTextPane.setBackground(UIManager.getColor("TabbedPane.background"));
        aboutTextPane.setHighlighter(null);

        tabbedPane.setFocusable(false);
        aboutPanel.setFocusable(false);
        rectangleButton.setFocusable(false);

        //Center text
        StyledDocument doc = aboutTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    public static void show() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        JFrame mainFrame = new JFrame();
        mainFrame.setContentPane(new ActionPicker().MainWindow);
        ImageIcon img = new ImageIcon("img\\icon.png");
        mainFrame.setIconImage(img.getImage());
        mainFrame.setTitle("GMM Tools");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(200, 300));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
