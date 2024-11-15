package io.github.akotu235.tsp.gui;

import javax.swing.*;
import java.awt.*;

public class FrameLocationManager {
    private static final Dimension screenSize;
    private static final int columns;
    private static final int chartWindowWidth;
    private static final int chartWindowHeight;
    private final int[] slots;
    private int windowCount = 0;

    static {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        double aspectRatio = (double) width / height;
        columns = aspectRatio > 1.78 ? 5 : 3;
        chartWindowWidth = screenSize.width / columns;
        chartWindowHeight = (int) (chartWindowWidth * 0.6);
    }

    public FrameLocationManager() {
        this.slots = new int[columns];
    }

    public static void setResultsFrameLocation(JFrame frame) {
        frame.setLocation(0, chartWindowHeight);
        frame.setPreferredSize(new Dimension(screenSize.width / 2, (int) (screenSize.width * 0.3)));
    }

    public static void setMainFrameLocation(JFrame frame) {
        frame.setLocation(screenSize.width / 2, chartWindowHeight);
    }

    public int setChartFrameLocation(JFrame frame) {
        int slot = getNextSlot();

        int x = slot * chartWindowWidth;
        int y = 0;

        windowCount++;

        frame.setLocation(x, y);
        frame.setSize(new Dimension(chartWindowWidth, chartWindowHeight));

        return slot;
    }

    public synchronized void releaseSlot(int slot) {
        slots[slot]--;
    }

    private synchronized int getNextSlot() {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == 0) {
                slots[i]++;
                return i;
            }
        }
        int slot = windowCount % columns;
        slots[slot]++;
        return slot;
    }
}