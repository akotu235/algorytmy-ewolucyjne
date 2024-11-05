package io.github.akotu235.tsp.gui;

import javax.swing.*;

public class DataModelFrame extends JFrame {
    public DataModelFrame() {
        setTitle("Create");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DataModelPanel dataModelPanel = new DataModelPanel();
        add(dataModelPanel);
        setVisible(false);
    }
}