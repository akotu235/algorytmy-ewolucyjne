package io.github.akotu235.tsp;

import io.github.akotu235.tsp.gui.MainFrame;

import javax.swing.*;

public class TravelingSalesmanApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}