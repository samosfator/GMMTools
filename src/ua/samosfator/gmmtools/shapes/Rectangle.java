package ua.samosfator.gmmtools.shapes;

import ua.samosfator.gmmtools.ActionPicker;
import ua.samosfator.gmmtools.RectSizeSetter;
import ua.samosfator.gmmtools.Screen;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Rectangle {
    public void draw(final int[][] points) throws Exception {
        final boolean[] isProperWindow = new boolean[1];

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    isProperWindow[0] = Screen.getTitle().startsWith("Google Map Maker");
                    if (isProperWindow[0]) {
                        timer.cancel();
                        timer.purge();
                        System.err.println("Window found. Started drawing.");

                        doDrawing(points);
                    } else System.err.println("...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    public void set() {
        RectSizeSetter rectSizeSetter = new RectSizeSetter();
    }

    protected void doDrawing(int[][] points) throws Exception {
        Robot bot = new Robot();
        for (int[] p : points) {
            bot.mouseMove(p[0], p[1]);
            bot.mousePress(InputEvent.BUTTON1_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_MASK);
            bot.delay(500);
        }
        ActionPicker.show();
    }
}
