package ua.samosfator.gmm.tools;

import java.awt.*;

public class Screen {
    private static final Rectangle screen = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getMaximumWindowBounds();

    public static int getWidth() {
        return screen.width;
    }
    public static int getHeight() {
        return screen.height;
    }
}