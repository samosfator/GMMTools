package ua.samosfator.gmm.tools.gui;

import ua.samosfator.gmm.tools.shapes.ApproximateDrawing;
import ua.samosfator.gmm.tools.shapes.DrawPolygon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionPicker {
    private JPanel MainWindow;
    private JButton rectangleButton;
    private JButton circleButton;
    private JPanel aboutPanel;
    private JTabbedPane tabbedPane;
    private JTextPane aboutTextPane;
    private JPanel selectShapePane;
    private JButton approxButton;
    private JButton addOutlineTemplateButton;
    private JList savedItemsList;
    private JButton startDrawingButton;

    public ActionPicker() {
        rectangleButton.addActionListener(e -> {
            hideMainWindow(e);
            RectSizeSetter rss = new RectSizeSetter();
        });
        rectangleButton.addActionListener(e -> {
            hideMainWindow(e);
            CircleSizeSetter rss = new CircleSizeSetter();
        });
        approxButton.addActionListener(e -> {
            hideMainWindow(e);
            try {
                ApproximateDrawing ad = new ApproximateDrawing();
                ad.start();
            } catch (Exception ignored) {}
        });
        addOutlineTemplateButton.addActionListener(e -> {
            hideMainWindow(e);
            DrawPolygon dp = new DrawPolygon();
            dp.start();
        });
        rectangleButton.setIcon(new ImageIcon("img\\rectangle.png"));
        circleButton.setIcon(new ImageIcon("img\\circle.png"));
        approxButton.setIcon(new ImageIcon("img\\approx.png"));

        aboutTextPane.setBackground(UIManager.getColor("TabbedPane.background"));
        aboutTextPane.setHighlighter(null);
        savedItemsList.setBorder(new EmptyBorder(2, 10, 0, 0));

        // Center text in JTextPane
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
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(200, 300));
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    private void hideMainWindow(ActionEvent e) {
        Window mainWindow = SwingUtilities.windowForComponent((Component) e.getSource());
        mainWindow.setVisible(false);
    }
}
