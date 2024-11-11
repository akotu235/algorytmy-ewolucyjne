package io.github.akotu235.tsp.gui;

import javax.swing.*;
import java.awt.*;

public class FrameAutoArranger {
    private final int windowWidth;
    private final int windowHeight;
    private final int columns;
    private int windowCount = 0;

    public FrameAutoArranger(int columns) {
        if (columns < 3) {
            columns = 3;
        }
        if (columns > 5) {
            columns = 5;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;

        this.windowWidth = screenWidth / columns;
        this.windowHeight = (int) (windowWidth * 0.6);

        this.columns = columns;
    }

    public void setChartFrameLocation(JFrame frame) {
        int column = windowCount % columns;

        int x = column * windowWidth;
        int y = 0;

        windowCount++;

        frame.setLocation(x, y);
        frame.setSize(new Dimension(windowWidth, windowHeight));
    }
}
