package io.github.akotu235.tsp.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Traveling Salesman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        MainPanel mainPanel = new MainPanel();
        add(mainPanel);
        pack();
    }
}