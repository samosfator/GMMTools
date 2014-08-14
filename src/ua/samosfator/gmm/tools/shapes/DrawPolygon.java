package ua.samosfator.gmm.tools.shapes;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.simplify.DouglasPeuckerSimplifier;
import ua.samosfator.gmm.tools.gui.ActionPicker;
import ua.samosfator.gmm.tools.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class DrawPolygon {
    private final JDialog frame = new JDialog();
    private static ArrayList<Coordinate> coords = new ArrayList<>();
    private List<Shape> shapes = new ArrayList<>();
    private Shape currentShape = null;

    public void start() {
        JComponent component = new JComponent() {
            {
            MouseAdapter mouseAdapter = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    currentShape = new Line2D.Double(e.getPoint(), e.getPoint());
                    shapes.add(currentShape);
                    coords.add(new Coordinate(e.getX(), e.getY()));
                    repaint();
                }

                public void mouseDragged(MouseEvent e) {
                    Line2D shape = (Line2D) currentShape;
                    shape.setLine(shape.getP1(), e.getPoint());
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    coords.add(new Coordinate(e.getX(), e.getY()));
                    currentShape = null;
                    repaint();
                    Robot bot;
                    try {
                        bot = new Robot();
                        bot.mousePress(InputEvent.BUTTON1_MASK);
                    } catch (AWTException ignored) { }
                }
            };
            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(Color.red);
                g2d.setStroke(new BasicStroke(7));
                shapes.forEach(g2d::draw);
            }
        };

        frame.setAlwaysOnTop(true);
        frame.getContentPane().add(component);
        frame.setMinimumSize(new Dimension(Screen.getWidth(), Screen.getHeight()));
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setBackground(new Color(1, 1, 1, (float) 0.1));
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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

    private void getNormalizedCoords() {
        coords.add(new Coordinate(coords.get(0)));
        Coordinate[] points = coords.toArray(new Coordinate[coords.size()]);
        GeometryFactory g = new GeometryFactory(new PrecisionModel(), 1);
        Geometry geo = g.createLinearRing(points);
        DouglasPeuckerSimplifier simpl = new DouglasPeuckerSimplifier(geo);
        simpl.setDistanceTolerance(15);
        save(simpl.getResultGeometry());
    }
    private void save(Geometry geometry) {
        //TODO: save corrds to disk
    }
}
