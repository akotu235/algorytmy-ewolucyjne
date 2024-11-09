package io.github.akotu235.tsp.gui;

import io.github.akotu235.tsp.chart.ResultsChart;
import io.github.akotu235.tsp.model.Results;
import io.github.akotu235.tsp.model.Route;

import javax.swing.*;
import java.awt.*;

public class ResultsFrame extends JFrame {
    private final Results results;

    public ResultsFrame(Results results) {
        setTitle("Result");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.results = results;
    }

    public void refresh() {
        getContentPane().removeAll();
        add(getPanel());
        repaint();
        revalidate();
        pack();
        setVisible(true);
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel chartPanel = new ResultsChart(results);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.add(getBestResultComponent(), BorderLayout.SOUTH);
        panel.add(getBestResultsComponent(), BorderLayout.EAST);
        return panel;
    }

    private JComponent getBestResultComponent() {
        JTextArea resultTextArea = new JTextArea(getShortestRoute());
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setBorder(BorderFactory.createTitledBorder("Najlepszy wynik"));
        return resultTextArea;
    }

    private JComponent getBestResultsComponent() {
        JTextArea resultTextArea = new JTextArea(results.toString());
        resultTextArea.setEditable(false);
        resultTextArea.setBorder(BorderFactory.createTitledBorder("Najlepsze wyniki"));
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(120, 200));
        return scrollPane;
    }

    private String getShortestRoute() {
        Route bestRoute = results.getShortestRoute();
        if (bestRoute == null) {
            return "";
        }
        return results.getShortestRoute().toString();
    }
}