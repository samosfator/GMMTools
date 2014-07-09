package ua.samosfator.gmmtools;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.simplify.DouglasPeuckerSimplifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ApproximateDrawing extends JPanel implements MouseMotionListener {
    final JDialog frame = new JDialog();
    private static ArrayList<Coordinate> coords = new ArrayList<>();
    private static Geometry resultGeometry;
    Coordinate[] points;
    private int mX, mY;

    public void start() {
        frame.setAlwaysOnTop(true);
        frame.getContentPane().add(new ApproximateDrawing());
        frame.setMinimumSize(new Dimension(Screen.getWidth(), Screen.getHeight()));
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setBackground(new Color(1, 1, 1, (float) 0.1));
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                boolean isEnter = e.getKeyCode() == KeyEvent.VK_ENTER;
                if (isEnter) {
                    getNormalizedCoords();
                    frame.setVisible(false);
                    ActionPicker.show();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.setVisible(false);
                    ActionPicker.show();
                }
            }
        });
    }

    public ApproximateDrawing() {
        addMouseMotionListener(this);
    }

    public void mouseMoved(MouseEvent me) {
        mX = (int) me.getPoint().getX();
        mY = (int) me.getPoint().getY();
        if (SwingUtilities.isLeftMouseButton(me)) {
            coords.add(new Coordinate(mX, mY));
            repaint();
        }
    }

    public void mouseDragged(MouseEvent me) {
        mouseMoved(me);
    }

    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(mX, mY, 5, 5);
    }

    private void getNormalizedCoords() {
        coords.add(new Coordinate(coords.get(0)));
        points = coords.toArray(new Coordinate[coords.size()]);
        GeometryFactory g = new GeometryFactory(new PrecisionModel(), 1);
        Geometry geo = g.createLinearRing(points);
        DouglasPeuckerSimplifier simpl = new DouglasPeuckerSimplifier(geo);
        simpl.setDistanceTolerance(15);
        resultGeometry = simpl.getResultGeometry();
        try {
            frame.setVisible(false);
            clickGeometry();
        } catch (AWTException ignored) { }
    }

    private void clickGeometry() throws AWTException {
        for (Coordinate c : resultGeometry.getCoordinates()) {
            Robot bot = new Robot();
            bot.mouseMove((int) c.x, (int) c.y);
            bot.mousePress(InputEvent.BUTTON1_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_MASK);
            bot.delay(500);
        }
        coords.clear();
    }
}
