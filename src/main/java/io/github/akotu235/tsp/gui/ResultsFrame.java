package io.github.akotu235.tsp.gui;

import io.github.akotu235.tsp.chart.ResultsChart;
import io.github.akotu235.tsp.model.Results;
import io.github.akotu235.tsp.model.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultsFrame extends JFrame {
    private final Results results;

    public ResultsFrame(Results results) {
        setTitle("Result");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.results = results;
    }

    public void refresh() {
        if (!isActive()) {
            setFocusableWindowState(false);
        }
        getContentPane().removeAll();
        add(getPanel());
        repaint();
        revalidate();
        pack();
        setVisible(true);
        setFocusableWindowState(true);
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
        String[] columnNames = {"", "Koszt [" + results.getCostUnit() + "]", "Czas [s]", "Generacja"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        model.addRow(new String[]{"średnio", String.format("%.0f", results.getAvgTotalCost()), results.getAvgDurationInSeconds(), results.getAvgGeneration()});
        for (int i = 0; i < results.size(); i++) {
            String totalCost = String.format("%.0f", results.get(i).route().totalCost());
            String duration = String.valueOf(results.get(i).duration().getSeconds());
            String generation = String.valueOf(results.get(i).generation());
            Object[] row = {i + 1, totalCost, duration, generation};
            model.addRow(row);
        }
        JTable table = new JTable(model);
        table.setEnabled(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(260, 200));
        return scrollPane;
    }

    private String getShortestRoute() {
        Route bestRoute = results.getBestResult().route();
        if (bestRoute == null) {
            return "";
        }
        return results.getBestResult().route().toString();
    }
}