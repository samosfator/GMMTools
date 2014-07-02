package ua.samosfator.gmmtools;

import ua.samosfator.gmmtools.shapes.Rectangle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RectSizeSetter {
    private JPanel TranslucentPane;
    private JLabel squareSet;
    private int[][] points = new int[5][2];

    public RectSizeSetter() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JDialog frame = new JDialog();
                frame.setAlwaysOnTop(true);
                frame.setContentPane(TranslucentPane);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                frame.setMinimumSize(new Dimension(Screen.getWidth(), Screen.getHeight()));
                frame.setUndecorated(true);
                Border border = BorderFactory.createLineBorder(new Color(221, 75, 57, 200), 5);
                squareSet.setPreferredSize(new Dimension(350, 250));
                squareSet.setBorder(border);
                squareSet.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                squareSet.setOpaque(true);
                squareSet.setBackground(new Color(1, 1, 1, (float) 0.3));
                squareSet.setFocusable(true);


                ComponentMover cm = new ComponentMover();
                cm.registerComponent(squareSet);
                ComponentResizer cr = new ComponentResizer();
                cr.setSnapSize(new Dimension(1, 1));
                cm.setDragInsets(cr.getDragInsets());
                cr.registerComponent(squareSet);


                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setBackground(new Color(0, 0, 0, 0));
                frame.setVisible(true);
            }
        });

        squareSet.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        getPosition();
                        hide();
                        Rectangle rectangle = new Rectangle();
                        rectangle.draw(points);
                    } catch (Exception ignored) {
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    points[0][0] = Screen.getWidth() / 2;
                    points[0][1] = Screen.getHeight() / 2;
                    hide();
                    ActionPicker.show();
                }
            }
        });
    }

    private void getPosition() {
        Point topLeft = squareSet.getLocation();
        Dimension size = squareSet.getSize();

        points[0][0] = topLeft.x;
        points[0][1] = (int) (topLeft.y + size.getHeight());
        points[1][0] = topLeft.x;
        points[1][1] = topLeft.y;

        points[2][0] = (int) (topLeft.x + size.getWidth());
        points[2][1] = topLeft.y;
        points[3][0] = (int) (topLeft.x + size.getWidth());
        points[3][1] = (int) (topLeft.y + size.getHeight());

        points[4][0] = points[0][0];
        points[4][1] = points[0][1];
    }

    private void hide() {
        squareSet.setFocusable(false);
        TranslucentPane.setVisible(false);
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException ignored) {
        }
        r.mouseMove(points[0][0], points[0][1]);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
